package com.example.intermediate.repository;

import com.example.intermediate.domain.PostCommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentHeartRepository extends JpaRepository<PostCommentHeart, Long> {
    Long countAllByCommentId(Long commentId);
    PostCommentHeart findByCommentIdAndMemberId(Long commentId, Long memberId);
}
