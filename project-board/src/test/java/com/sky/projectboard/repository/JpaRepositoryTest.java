package com.sky.projectboard.repository;

import com.sky.projectboard.config.JpaConfig;
import com.sky.projectboard.domain.Article;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("testdb")
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

//    @DisplayName("insert 테스트")
//    @Test
//    void givenTestData_whenInserting_thenWorksFine() {
//        // Given
//        long previousCount = articleRepository.count();
//        // When
//        Article savedArticle = articleRepository.save(Article.of("new article","new content", "#spring"));
//        // Then
//
//        assertThat(articleRepository.count()).isEqualTo(previousCount+1);
//    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);
        // When
        Article savedArticle = articleRepository.saveAndFlush(article);
        // Then

        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);
        // Then

        assertThat(articleRepository.count()).isEqualTo(previousArticleCount -1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);

    }
}