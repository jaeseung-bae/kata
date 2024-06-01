package com.github.practice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.practice.domain.Article;
import com.github.practice.repository.ArticleRepository;

import jakarta.transaction.Transactional;
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
            throw new IllegalArgumentException("invalid id=" + id);
        }
        article.patch(newArticle);
        repository.save(article);
        return article;
    }

    public void delete(Long id) {
        Article article = repository.findById(id).orElse(null);
        if (article == null) {
            throw new IllegalArgumentException("invalid id=" + id);
        }
        repository.delete(article);
    }

    @Transactional
    public List<Article> batchSave(List<Article> articles) {
        return articles.stream()
                       .map(repository::save)
                       .collect(Collectors.toList());
    }
}
