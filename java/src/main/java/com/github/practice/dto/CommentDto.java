package com.github.practice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDto {
    private Long id;
    @NotBlank
    private Long articleId;
    @NotBlank(message = "it's required field")
    private String nickname;
    @NotBlank(message = "it's required field")
    private String content;
}
