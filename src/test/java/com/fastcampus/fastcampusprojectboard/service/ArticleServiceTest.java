package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.ArticleUpdateDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    // @InjectMocks vs Mock 비교 : 1) InjectMocks는 생성자 파라미터에 사용할 수 없다
    @InjectMocks private ArticleService sut; // sut : System Under Test
    @Mock private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        // given
//        SearchParameters param = SearchParameters.of(SearchType.TITLE, "search keyword");

        // when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그

        // then
        assertThat(articles).isNotNull();

    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다")
    @Test
    void givenArtcleId_whenSearchingArticle_thenReturnsArticle() {
        // given


        // when
        ArticleDto article = sut.searchArticle(1L); // 제목, 본문, ID, 닉네임, 해시태그

        // then
        assertThat(article).isNotNull();

    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // given
        ArticleDto articleDto = ArticleDto.of(
                LocalDateTime.now(),
                "admin",
                "title",
                "content",
                "#java"
        );
        given(articleRepository.save(any(Article.class))).willReturn(null);
//        willDoNothing().given(articleRepository.save(any(Article.class))); // JPA 메서드는 void가 아니므로 적절하지 않음

        // when
        sut.saveArticle(articleDto);

        // then
        then(articleRepository).should().save(any(Article.class)); // 호출까지만 확인하는 유닛 테스트 ~ solitary 테스트
        // cf) 실제로 해당 데이터가 데이터베이스까지 영향을 끼쳤는지를 보는 sociable 테스트

    }

    @DisplayName("게시글 ID와 수정정보를 입력하면, 게시글을 수정한다")
    @Test
    void givenArticleIdAndInfo_whenUpdatingArticle_thenUpdatesArticle() {
        // given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // when
        sut.updateArticle(1L, ArticleUpdateDto.of("title2","content2","#java2"));

        // then
        then(articleRepository).should().save(any(Article.class));

    }

    @DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // when
        sut.deleteArticle(1L);

        // then
        then(articleRepository).should().delete(any(Article.class));

    }
}