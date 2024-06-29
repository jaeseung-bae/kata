package com.github.practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDto {
    private Long id;
    @JsonProperty("article-id")
    private Long articleId;
    private String nickname;
    private String content;
}
