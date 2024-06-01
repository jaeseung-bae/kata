package com.github.practice.controller;

import java.util.List;

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
import com.github.practice.dto.ArticleBatchCreateRequestDTO;
import com.github.practice.dto.ArticleBatchCreateResponseDTO;
import com.github.practice.dto.ArticleFormDTO;
import com.github.practice.repository.ArticleRepository;
import com.github.practice.service.ArticleService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class ArticleApiController {
    private final ArticleRepository repository;
    private final ArticleService service;

    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleFormDTO dto) {
        Article saved = service.save(dto.toEntity());
        log.info(saved.toString());
        return saved;
    }

    @PostMapping("/api/articles:batchCreate")
    public ResponseEntity<ArticleBatchCreateResponseDTO> batchCreate(
            @RequestBody ArticleBatchCreateRequestDTO dto) {
        List<Article> articles = service.batchSave(dto.toEntityList());
        log.info(articles.toString());
        ArticleBatchCreateResponseDTO response = new ArticleBatchCreateResponseDTO(articles);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/articles/{id}")
    public Article getArticle(@PathVariable("id") Long id) {
        return service.findById(id).orElse(null);
    }

    @GetMapping("/api/articles")
    public Page<Article> listArticles(Pageable pageable) {
        return service.findAll(pageable);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> patch(@PathVariable("id") Long id, @RequestBody ArticleFormDTO dto) {
        log.info("id={}, request={}", id, dto.toString());
        Article article = service.patch(id, dto.toEntity());
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
