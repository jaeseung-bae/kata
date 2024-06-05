package com.github.practice.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.practice.domain.Article;
import com.github.practice.domain.Comment;
import com.github.practice.dto.CommentDto;
import com.github.practice.repository.CommentRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;
    @Autowired
    private ArticleService articleService;

    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Comment createComment(CommentDto commentDto) {
        Article article = articleService.findById(commentDto.getArticleId()).orElseThrow();
        Comment comment = Comment.create(commentDto.getNickname(), commentDto.getContent(), article);
        repository.save(comment);
        return comment;
    }

    public List<Comment> findAllByArticleId(Long articleId) {
        return repository.findByArticleId(articleId);
    }

    public Comment patchComment(Long id, CommentDto commentDto) {
        Comment comment = repository.findById(id).orElseThrow();
        comment.patch(commentDto.getNickname(), commentDto.getContent());
        repository.save(comment);
        return comment;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
