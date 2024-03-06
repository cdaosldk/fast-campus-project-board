package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("testdb") // 테스트 설정용 프로파일 yaml과 매핑
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 디비를 사용하도록 설정
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                      @Autowired ArticleCommentRepository articleCommentRepository,
                      @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select test")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // given

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert test")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("Uno", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new contents", "#spring");

        // when
        articleRepository.save(article);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update test")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // when
        // 영속화을 위해 flush 수행
        Article updatedArticle = articleRepository.saveAndFlush(article);

        // then
        assertThat(updatedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete test")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousCountArticle = articleRepository.count();
        long previousCountComment = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();


        // when
        // 영속화을 위해 flush 수행
        articleRepository.delete(article);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousCountArticle - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousCountComment - deletedCommentSize);
    }
}
