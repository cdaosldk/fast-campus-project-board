package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) { // 자바 12부터 가능
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag(searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId)); // 운영 편의를 위한 로깅
    }

    public void saveArticle(ArticleDto articleDto) {
        articleRepository.save(articleDto.toEntity());
    }

    public void updateArticle(ArticleDto articleDto) {
        // getReferenceById가 아닐 경우 ~ 기존의 getOne() 메서드를 대체함 : 스프링부트 2.7부터
//        Article article = articleRepository.findById(articleDto.id());// DTO에 해당하는 데이터가 실제로 있는지 쿼리를 날려서 확인하고 가져온다
//        article.setHashtag(good);
//        articleRepository.save(article); // ~ 영속성 컨텍스트에서 무조건 가져와야 하기 때문에 셀렉 쿼리 발생

        try {
            Article article = articleRepository.getReferenceById(articleDto.id()); // EntitiyNotFoundException
            if (articleDto.title() != null) article.setTitle(articleDto.title()); // getter, setter는 레코드에서 자동으로 만든다
            if (articleDto.content() != null) article.setContent(articleDto.content());
            if (articleDto.hashtag() != null) article.setHashtag(articleDto.hashtag());

            // 현재 해당 메서드는 트랜젝션이 설정되어 있으므로 영속성 컨텍스트는 메서드 수행 후 내용 변경을 감지하고
            // 업데이트 쿼리를 자동으로 날린다 (수동으로 설정할 수 있다)
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패, 게시글을 찾을 수 없습니다. - articleDto: {}", articleDto); // 인터폴레이션
        }

    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }


    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }
        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}
