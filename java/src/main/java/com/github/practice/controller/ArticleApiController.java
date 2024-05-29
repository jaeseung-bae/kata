package com.github.practice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public Article getArticle(@PathVariable("id") Long id) {
        Article article = repository.findById(id).orElse(null);
        return article;
    }

    @GetMapping("/api/articles")
    public Page<Article> listArticles(Pageable pageable) {
        Page<Article> all = repository.findAll(pageable);
        return all;
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> patch(@PathVariable("id") Long id, @RequestBody ArticleFormDTO dto) {
        log.info("id={}, request={}", id, dto.toString());
        Article article = repository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        article.patch(dto.toEntity());
        log.info("updatedArticle={}", article);
        repository.save(article);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Article article = repository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        repository.delete(article);
        return ResponseEntity.noContent().build();
    }
}
