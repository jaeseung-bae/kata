package com.github.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.practice.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * " +
                   "FROM comment " +
                   "WHERE article_id = :articleId",
            nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);

    List<Comment> findByNickname(String nickname);
}
