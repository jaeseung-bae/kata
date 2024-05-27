package com.github.practice.dto;

import com.github.practice.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ArticleFormDTO {
    private Long id;
    private String title;
    private String content;
    public Article toEntity() {
        return new Article(id, title, content);
    }
}
