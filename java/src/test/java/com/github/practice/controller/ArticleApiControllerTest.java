package com.github.practice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.practice.dto.ArticleFormDTO;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleApiControllerTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createArticle() throws Exception {
        // Arrange
        ArticleFormDTO requestDTO = new ArticleFormDTO(null, "title", "content");
        String body = mapper.writeValueAsString(requestDTO);

        // Act

        // Assert
        mockMvc.perform(post("/api/articles")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(header().exists("Location"));
    }

    @DisplayName("invalid title length")
    @Test
    void createArticleFail() throws Exception {
        // Arrange
        ArticleFormDTO requestDTO = new ArticleFormDTO(null, "", "content");
        String body = mapper.writeValueAsString(requestDTO);

        // Act

        // Assert
        mockMvc.perform(post("/api/articles")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @DisplayName("invalid content length")
    @Test
    void createArticleFail2() throws Exception {
        // Arrange
        ArticleFormDTO requestDTO = new ArticleFormDTO(null, "title", "");
        String body = mapper.writeValueAsString(requestDTO);

        // Act

        // Assert
        mockMvc.perform(post("/api/articles")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    void getArticle() throws Exception {
        // Arrange
        String createdArticleUri = postArticle("title", "content");

        // Act
        ResultActions resultActions = mockMvc.perform(get(createdArticleUri));

        // Assert
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(createdArticleUri)
                                                          .build();
        String articleId = uriComponents.getPathSegments().get(uriComponents.getPathSegments().size() - 1);
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.id").value(articleId))
                     .andExpect(jsonPath("$.title").value("title"))
                     .andExpect(jsonPath("$.content").value("content"));
    }

    @DisplayName("get none-exist article")
    @Test
    void getArticleFail() throws Exception {
        // Arrange

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/articles/0"));

        // Assert
        resultActions.andExpect(status().isNotFound());
    }

    private String postArticle(String title, String content) throws Exception {
        ArticleFormDTO requestDTO = new ArticleFormDTO(null, title, content);
        String body = mapper.writeValueAsString(requestDTO);
        MvcResult result = mockMvc.perform(post("/api/articles")
                                                   .content(body)
                                                   .contentType(MediaType.APPLICATION_JSON))
                                  .andExpect(status().isCreated())
                                  .andReturn();
        String createdArticleUri = result.getResponse().getHeader("Location");
        assertThat(createdArticleUri).isNotNull();
        return createdArticleUri;
    }

    @Test
    void listArticles() throws Exception {
        // Arrange
        int numOfNewArticles = 3;
        for (int i = 0; i < numOfNewArticles; i++) {
            postArticle("title", "content");
        }

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/articles"));

        // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.pageable").exists())
                     .andExpect(jsonPath("$.numberOfElements", greaterThanOrEqualTo(2)))
                     .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void updateArticle() throws Exception {
        // Arrange
        String originalTitle = "title";
        String createdArticleUri = postArticle(originalTitle, "content");
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(createdArticleUri)
                                                          .build();
        String articleId = uriComponents.getPathSegments().get(uriComponents.getPathSegments().size() - 1);
        ResultActions resultActions = mockMvc.perform(get(createdArticleUri));
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.id").value(articleId))
                     .andExpect(jsonPath("$.title").value(originalTitle))
                     .andExpect(jsonPath("$.content").value("content"));

        // Act
        String modifiedTitle = "modifiedTitle";
        resultActions = mockMvc.perform(
                patch(createdArticleUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new ArticleFormDTO(null, modifiedTitle, null)))
        );

        // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.id").value(articleId))
                     .andExpect(jsonPath("$.title").value(modifiedTitle))
                     .andExpect(jsonPath("$.content").value("content"));
    }

    @Test
    void deleteArticle() throws Exception {
        // Arrange
        String createdArticleUri = postArticle("title", "content");
        mockMvc.perform(get(createdArticleUri))
               .andExpect(status().isOk());

        // Act
        ResultActions resultActions = mockMvc.perform(delete(createdArticleUri));

        // Assert
        resultActions.andExpect(status().isNoContent());
    }
}

