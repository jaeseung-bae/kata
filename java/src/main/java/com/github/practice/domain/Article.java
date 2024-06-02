package com.github.practice.domain;

import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    public void patch(Article article) {
        title = Optional.ofNullable(article.getTitle())
                        .filter(t -> !t.isBlank())
                        .orElse(title);
        content = Optional.ofNullable(article.getContent())
                          .filter(t -> !t.isBlank())
                          .orElse(content);
    }
}
