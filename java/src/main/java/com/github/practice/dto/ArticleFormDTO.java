package com.github.practice.dto;

import com.github.practice.domain.Article;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ArticleFormDTO {
    @Positive
    @Nullable
    private Long id;
    @Size(min = 1)
    private String title;
    @Size(min = 1)
    private String content;
    public Article toEntity() {
        return new Article(id, title, content);
    }
}
