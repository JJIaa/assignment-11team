package com.sparta.innovationcamp.scheduler;

import com.sparta.innovationcamp.domain.Post;
import com.sparta.innovationcamp.repository.PostRepository;
import com.sparta.innovationcamp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final PostRepository postRepository;
    private final PostService postService;

    //정해진 시간에 다음과 같은 메서드 실행. 초, 분, 시, 일, 월, 주 순서
    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void updatePostDelete() throws InterruptedException {
        System.out.println("게시글 업데이트");

        //저장된 모든 게시글 조회
        List<Post> postList = postRepository.findAll();

//        for (int i = 0; i < postList.size(); i++) {
//            Post post = postList.get(i);
//            Long id = post.getId();
//            String title = post.getTitle();
//
////            System.out.println(post.getComments());
//            if (post.getComments().size() == 0) {
//                postService.deleteBySearch(id);
//                System.out.println("게시물 <" + title + ">이 삭제되었습니다.");
//            }
//        }

        boolean check = false;
        for (Post postOne : postList) {
            if (postOne.getComments().size()==0) {
                System.out.println("게시글 " + postOne.getTitle() + "이 삭제되었습니다.");
                postRepository.delete(postOne);
                check = true;
            }
        }

        if (check == false) {
            System.out.println("삭제할 게시글이 없습니다.");
        }
    }
}