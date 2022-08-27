package com.example.intermediate.scheduler;

import com.example.intermediate.domain.Post;
import com.example.intermediate.repository.PostRepository;
import com.example.intermediate.service.PostService;
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
    @Scheduled(cron = "0 00 01 * * *")
    public void updatePostDelete() throws InterruptedException {
        System.out.println("게시글 업데이트");

        //저장된 모든 코멘트와 게시글 조회
        List<Post> postList = postRepository.findAll();

        for (int i = 0; i < postList.size(); i++) {
            Post post = postList.get(i);
            Long id = post.getId();
            String title = post.getTitle();
            if (post.getComments().size() == 0) {
                postService.deleteBySearch(id);
                System.out.println("게시물 <" + post.getTitle() + ">이 삭제되었습니다.");
            }
        }
    }
}
