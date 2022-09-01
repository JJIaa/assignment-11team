package com.example.intermediate.scheduler;

import com.example.intermediate.domain.Post;
import com.example.intermediate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Scheduler {

    private final PostRepository postRepository;

    //정해진 시간에 다음과 같은 메서드 실행. 초, 분, 시, 일, 월, 주 순서
    @Transactional
    @Scheduled(cron = "0 43 15 * * *")
    public void updatePostDelete() throws InterruptedException {
        System.out.println("게시글 업데이트");

        //저장된 모든 게시글 조회
        List<Post> postList = postRepository.findAll();

        //postList안에 댓글이 없다면 지우고
        boolean check = false;
        for (Post postOne : postList) {
            if (postOne.getComment_cnt()==0) {
                System.out.println("게시글 " + postOne.getTitle() + "이 삭제되었습니다.");
                postRepository.delete(postOne);
                check = true;
            }
        }
        //댓글이 있다면 삭제하지 않고 다음과 같은 메세지 콘솔에 찍기.
        if (check == false) {
            System.out.println("삭제할 게시글이 없습니다.");
        }
    }
}