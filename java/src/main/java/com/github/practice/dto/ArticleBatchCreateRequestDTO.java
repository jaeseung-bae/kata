package com.github.practice.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.github.practice.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArticleBatchCreateRequestDTO {
    private List<ArticleFormDTO> articles;
    public List<Article> toEntityList() {
        return articles.stream()
                       .map(ArticleFormDTO::toEntity)
                       .collect(Collectors.toList());
    }
}
