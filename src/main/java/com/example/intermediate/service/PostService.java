package com.example.intermediate.service;

import com.example.intermediate.controller.response.*;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.controller.request.PostRequestDto;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final PostHeartRepository postHeartRepository;
  private final PostCommentHeartRepository postCommentHeartRepository;

  private final S3UploaderService s3UploaderService;
  private final ImageRepository imageRepository;

  private final TokenProvider tokenProvider;

  @Transactional
  public ResponseDto<?> createPost(PostRequestDto requestDto, MultipartFile multipartFile, HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }

    //AWS
    String FileName = null;
    if (multipartFile.isEmpty()) {
      return ResponseDto.fail("INVALID_FILE", "파일이 유효하지 않습니다.");
    }
    ImageResponseDto imageResponseDto = null;
    try {
      FileName = s3UploaderService.uploadFile(multipartFile, "image");
      imageResponseDto = new ImageResponseDto(FileName);
    } catch (Exception e) {
      e.printStackTrace();
    }


    assert imageResponseDto != null;
    Post post = Post.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .comment_cnt(0)
            .image(imageResponseDto.getImageUrl())
            .member(member)
            .build();
    postRepository.save(post);

    return ResponseDto.success(
            PostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getMember().getNickname())
                    .imageUrl(post.getImage())
                    .heartNum(post.getHeartNum())
                    .comment_cnt(post.getComment_cnt())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build()
    );
  }

  @Transactional(readOnly = true)
  public ResponseDto<?> getPost(Long id) {
    Post post = isPresentPost(id);
    if (null == post) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    List<Comment> commentList = commentRepository.findAllByPostAndParent(post, null);
    List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

    for (Comment comment : commentList) {
      commentResponseDtoList.add(
          CommentResponseDto.builder()
              .id(comment.getId())
              .author(comment.getMember().getNickname())
              .content(comment.getContent())
              .heartNum(postCommentHeartRepository.countAllByCommentId(comment.getId()))
              .createdAt(comment.getCreatedAt())
              .modifiedAt(comment.getModifiedAt())
              .replies(replyListExtractor(post, comment))
              .build()
      );
    }

    Long heartNum = postHeartRepository.countAllByPostId(post.getId());
//    String imageUrl = post.getImage();

    return ResponseDto.success(
        PostResponseDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .imageUrl(post.getImage())
            .comment_cnt(post.getComment_cnt())
            .author(post.getMember().getNickname())
            .heartNum(heartNum)
            .createdAt(post.getCreatedAt())
            .modifiedAt(post.getModifiedAt())
            .comments(commentResponseDtoList)
            .build()
    );
  }

  @Transactional(readOnly = true)
  public ResponseDto<?> getAllPost() {

    List<Post> allPosts = postRepository.findAllByOrderByModifiedAtDesc();
    List<GetAllPostResponseDto> getAllPostResponseDtoList = new ArrayList<>();

    for (Post post : allPosts) {
      getAllPostResponseDtoList.add(
              GetAllPostResponseDto.builder()
                      .id(post.getId())
                      .title(post.getTitle())
                      .author(post.getMember().getNickname())
                      .heartNum(postHeartRepository.countAllByPostId(post.getId()))
                      .comment_cnt(post.getComment_cnt())
                      .createdAt(post.getCreatedAt())
                      .modifiedAt(post.getModifiedAt())
                      .build()
      );
    }
    return ResponseDto.success(getAllPostResponseDtoList);
  }

  @Transactional
  public ResponseDto<?> updatePost(Long id, PostRequestDto requestDto, MultipartFile multipartFile, HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }

    Post post = isPresentPost(id);
    if (null == post) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    if (post.validateMember(member)) {
      return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
    }

    //AWS
    String FileName = null;
    if (multipartFile.isEmpty()) {
      return ResponseDto.fail("INVALID_FILE", "파일이 유효하지 않습니다.");
    }
    ImageResponseDto imageResponseDto = null;
    if (!multipartFile.isEmpty()) {
      try {
        FileName = s3UploaderService.uploadFile(multipartFile, "image");
        imageResponseDto = new ImageResponseDto(FileName);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    assert imageResponseDto != null;
    post.update(requestDto, imageResponseDto);

    return ResponseDto.success(
            PostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imageUrl(post.getImage())
                    .comment_cnt(post.getComment_cnt())
                    .author(post.getMember().getNickname())
                    .heartNum(postHeartRepository.countAllByPostId(post.getId()))
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build()
    );
  }

  @Transactional
  public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }

    Post post = isPresentPost(id);
    if (null == post) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    if (post.validateMember(member)) {
      return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
    }

    postRepository.delete(post);
    return ResponseDto.success("delete success");
  }

  @Transactional(readOnly = true)
  public Post isPresentPost(Long id) {
    Optional<Post> optionalPost = postRepository.findById(id);
    return optionalPost.orElse(null);
  }

  @Transactional
  public List<ReplyResponseDto> replyListExtractor(Post post, Comment parent_comment) {

    List<Comment> replyList = commentRepository.findAllByPostAndParent(post, parent_comment);
    List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();

    for (Comment reply : replyList) {
      replyResponseDtoList.add(
              ReplyResponseDto.builder()
                      .id(reply.getId())
                      .parentId(parent_comment.getId())
                      .author(reply.getMember().getNickname())
                      .content(reply.getContent())
                      .heartNum(postCommentHeartRepository.countAllByCommentId(reply.getId()))
                      .createdAt(reply.getCreatedAt())
                      .modifiedAt(reply.getModifiedAt())
                      .build()
      );
    }
    return replyResponseDtoList;
  }

  @Transactional
  public Member validateMember(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return null;
    }
    return tokenProvider.getMemberFromAuthentication();
  }

}
