package com.github.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.practice.domain.Article;
import com.github.practice.dto.ArticleFormDTO;
import com.github.practice.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ArticleApiController {
    private final ArticleRepository repository;

    public ArticleApiController(ArticleRepository repository) {this.repository = repository;}

    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleFormDTO dto) {
        Article saved = repository.save(dto.toEntity());
        log.info(saved.toString());
        return saved;
    }

    @GetMapping("/api/articles/{id}")
    public Article getArticle(@PathVariable Long id) {
        Article article = repository.findById(id).orElse(null);
        return article;
    }

    @GetMapping("/api/articles")
    public Page<Article> listArticles(Pageable pageable) {
        Page<Article> all = repository.findAll(pageable);
        return all;
    }
}
