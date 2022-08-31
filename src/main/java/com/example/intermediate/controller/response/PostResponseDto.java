package com.example.intermediate.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
  private Long id;
  private String title;
  private String author;
  private String content;
  private Long heartNum;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private Integer comment_cnt;
  private List<CommentResponseDto> comments;
}
