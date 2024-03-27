package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>
    , QuerydslPredicateExecutor<ArticleComment>
    , QuerydslBinderCustomizer<QArticleComment> {

    List<ArticleComment> findByArticle_Id(Long articleId); // 게시글 ID로 댓글 리스트를 조회하기 때문에 _id 사용

    void deleteByIdAndUserAccount_UserId(Long articleId, String userId);

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        bindings.excludeUnlistedProperties(true); // 선택한 필드만 검색이 가능하게 만든다
        bindings.including(root.createdAt, root.createdBy, root.content); // 제목, 해시태그, 생성일시, 생성자, 본문 검색 허용
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 대소문자 구분없이 일치하는 표현을 포함하는 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 일치하는 표현을 포함하는 검색
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 일치하는 표현을 포함하는 검색
    }
}

