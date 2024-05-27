package com.github.practice.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.github.practice.domain.Article;


public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
}
