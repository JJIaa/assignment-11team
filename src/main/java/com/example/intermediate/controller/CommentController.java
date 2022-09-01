package com.example.intermediate.controller;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.controller.request.CommentRequestDto;
import com.example.intermediate.service.CommentService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

  private final CommentService commentService;

  @RequestMapping(value = "/api/auth/comment", method = RequestMethod.POST)
  public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
      HttpServletRequest request) {
    return commentService.createComment(requestDto, request);
  }

  @RequestMapping(value = "/api/auth/comment/{comment_id}", method = RequestMethod.POST)
  public ResponseDto<?> createReply(@PathVariable Long comment_id, @RequestBody CommentRequestDto requestDto,
      HttpServletRequest request) {
    return commentService.createReply(comment_id, requestDto, request);
  }

  @RequestMapping(value = "/api/comment/{post_id}", method = RequestMethod.GET)
  public ResponseDto<?> getAllComments(@PathVariable Long post_id) {
    return commentService.getAllCommentsByPost(post_id);
  }

  @RequestMapping(value = "/api/auth/comment/{comment_id}", method = RequestMethod.PUT)
  public ResponseDto<?> updateComment(@PathVariable Long comment_id, @RequestBody CommentRequestDto requestDto,
      HttpServletRequest request) {
    return commentService.updateComment(comment_id, requestDto, request);
  }

  @RequestMapping(value = "/api/auth/comment/{comment_id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deleteComment(@PathVariable Long comment_id,
      HttpServletRequest request) {
    return commentService.deleteComment(comment_id, request);
  }
}
