package com.github.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDto {
    private Long id;
    private Long articleId;
    private String nickname;
    private String content;
}
