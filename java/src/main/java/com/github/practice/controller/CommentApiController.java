package com.github.practice.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.practice.domain.Comment;
import com.github.practice.dto.CommentDto;
import com.github.practice.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CommentApiController {
    @Autowired
    private CommentService service;

    @PostMapping("/api/comments")
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
        Comment comment = service.createComment(commentDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                                             .path("/{id}")
                                             .buildAndExpand(comment.getId())
                                             .toUri();
        log.info("comment created uri={}", uri);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/api/comments/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id) {
        Optional<Comment> comment = service.findById(id);
        return comment.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/comments")
    public ResponseEntity<List<CommentDto>> findAllCommentByArticleId(
            @RequestParam("article-id") Long articleId) {
        // TODO: Pagination, Filter(optional)
        List<Comment> comments = service.findAllByArticleId(articleId);
        List<CommentDto> commentDtoList = comments.stream()
                                                  .map(comment -> new CommentDto(comment.getId(),
                                                                                 comment.getArticle().getId(),
                                                                                 comment.getNickname(),
                                                                                 comment.getContent()))
                                                  .collect(Collectors.toList());
        return ResponseEntity.ok(commentDtoList);
    }

    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<Comment> patchComment(@PathVariable("id") Long id,
                                                @RequestBody CommentDto commentDto) {
        Comment comment = service.patchComment(id, commentDto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
