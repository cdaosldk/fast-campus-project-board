package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.QArticle;
import com.fastcampus.fastcampusprojectboard.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>
    ,   ArticleRepositoryCustom
    ,   QuerydslPredicateExecutor<Article> // "검색" 기능을 위해서는 PE만 있어도 된다, "완전 일치"
    ,   QuerydslBinderCustomizer<QArticle> {

    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String nickName, Pageable pageable);
    Page<Article> findByHashtag(String hashtag, Pageable pageable);

    void deleteByIdAndUserAccount_UserId(Long articleId, String userId);

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) { // 확장 메서드
        bindings.excludeUnlistedProperties(true); // 선택한 필드만 검색이 가능하게 만든다
        bindings.including(root.title, root.hashtag, root.createdAt, root.createdBy, root.content); // 제목, 해시태그, 생성일시, 생성자, 본문 검색 허용
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 일치하는 표현을 포함하는 검색
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 일치하는 표현을 포함하는 검색
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 대소문자 구분없이 일치하는 표현을 포함하는 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 일치하는 표현을 포함하는 검색
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 일치하는 표현을 포함하는 검색
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); 쿼리가 like '${value}로 되기 때문에 %를 직접 삽입해야 한다!
    }
}