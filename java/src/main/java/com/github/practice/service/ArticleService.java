package com.github.practice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.practice.domain.Article;
import com.github.practice.repository.ArticleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository repository;

    public Article save(Article article) {
        return repository.save(article);
    }

    public Optional<Article> findById(Long id) {
        return repository.findById(id);
    }

    public Page<Article> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Article patch(Long id, Article newArticle) {
        Article article = repository.findById(id).orElse(null);
        if (article == null) {
            throw new IllegalArgumentException("Article not found");
        }
        article.patch(newArticle);
        repository.save(article);
        return article;
    }

    public void delete(Long id) {
        Article article = repository.findById(id).orElse(null);
        if (article == null) {
            throw new IllegalArgumentException("Article not found");
        }
        repository.delete(article);
    }
}
