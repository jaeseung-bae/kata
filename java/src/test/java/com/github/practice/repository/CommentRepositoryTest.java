package com.github.practice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.github.practice.domain.Article;
import com.github.practice.domain.Comment;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;
    private Article article;

    @Test
    void findByArticleId() {
        // Arrange
        Article article = new Article(null, "title", "content");
        Article saved = articleRepository.save(article);
        int n = 3;
        for (int i = 0; i < n; i++) {
            Comment comment = new Comment(null, saved, "Brian", "comment content");
            commentRepository.save(comment);
        }

        // Act
        List<Comment> comments = commentRepository.findByArticleId(saved.getId());

        // Assert
        assertThat(comments).hasSize(n);
    }

    @Test
    void findByNickname() {
        // Arrange
        Article article = new Article(null, "title", "content");
        Article saved = articleRepository.save(article);
        String expectedNickname = "Brian";
        int n = 3;
        for (int i = 0; i < n; i++) {
            Comment comment = new Comment(null, saved, expectedNickname, "comment content");
            commentRepository.save(comment);
        }

        // Act
        List<Comment> comments = commentRepository.findByNickname(expectedNickname);

        // Assert
        assertThat(comments).hasSize(n);
        assertThat(comments)
                .extracting(Comment::getNickname)
                .allMatch(nickname -> nickname.equals(expectedNickname));
    }

    @BeforeEach()
    void setUp() {
        Article article = new Article(null, "title", "content");
        this.article = articleRepository.save(article);
    }

    @Test
    void test() {
        // Arrange
        String expectedNickname = "Brian";
        Comment comment = new Comment(null, article, expectedNickname, "comment content");
        commentRepository.save(comment);

        // Act
        assertTrue(commentRepository.existsById(comment.getId()));
        assertTrue(commentRepository.existsByNickname(expectedNickname));
        assertThat(commentRepository.readByNickname(expectedNickname).get().getNickname()).isEqualTo(expectedNickname);

        // Assert

    }
}