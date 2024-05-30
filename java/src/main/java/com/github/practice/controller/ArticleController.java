package com.github.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.practice.domain.Article;
import com.github.practice.dto.ArticleFormDTO;
import com.github.practice.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    ArticleRepository repository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleFormDTO dto) {
        Article entity = dto.toEntity();
        Article saved = repository.save(entity);
        log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/hi")
    public String hi(Model model) {
        model.addAttribute("username", "주아");
        return "hi";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Article article = repository.findById(id).orElse(null);
        if (article == null) {
            return "articles/show";
        }
        model.addAttribute("article", article);
        return "articles/show";
    }

    @GetMapping("/articles")
    public String listArticles(Model model) {
        List<Article> articles = repository.findAll();
        model.addAttribute("articleList", articles);
        return "articles/article-list";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Article article = repository.findById(id).orElse(null);
        model.addAttribute("article", article);
        return "/articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleFormDTO dto) {
        Article article = repository.findById(dto.getId()).orElse(null);
        if (article == null) {
            return "redirect:/articles";
        }
        Article updatedArticle = dto.toEntity();
        repository.save(updatedArticle);
        return "redirect:/articles/"+updatedArticle.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Article article = repository.findById(id).orElse(null);
        log.info("delete article {}", article);
        if (article == null) {
            return "redirect:/articles";
        }
        repository.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Deleted article " + article.getTitle());
        return "redirect:/articles";
    }
}
