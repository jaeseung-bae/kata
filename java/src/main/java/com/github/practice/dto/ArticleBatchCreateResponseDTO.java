package com.github.practice.dto;

import java.util.List;

import com.github.practice.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArticleBatchCreateResponseDTO {
    List<Article> articles;
}
