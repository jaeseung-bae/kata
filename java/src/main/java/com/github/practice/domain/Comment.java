package com.github.practice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Comment {
    public static Comment create(String nickname, String content, Article article) {
        return new Comment(null, article, nickname, content);
    }

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private String nickname;
    private String content;

    public void patch(String nickname, String content) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (content != null) {
            this.content = content;
        }
    }
}
