package com.example.intermediate.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
  private Long postId;
  private String content;
  private Long parent_comment_id;

  public CommentRequestDto(Long postId, String content) {
    this.postId = postId;
    this.content = content;
    this.parent_comment_id = null;
  }
}
