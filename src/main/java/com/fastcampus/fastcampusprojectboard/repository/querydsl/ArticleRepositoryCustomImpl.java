package com.fastcampus.fastcampusprojectboard.repository.querydsl;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom { // cf) impl은 약속 ~ 설정으로 바꿀 수 있지만 번거롭다

    public ArticleRepositoryCustomImpl() {
        super(Article.class); // 이 생성자를 통해 해당 클래스가 ArticleRepository의 queryDsl이라는 것을 알려주는 방법
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

        // 여기서 select(article)하면 일반 JPA로 객체 전체를 조회하는 것과 같으므로 queryDsl을 사용하는 의미가 없다
        return from(article)
                .distinct()
                .select(article.hashtag) // 특별한 컬럼만 보내는 것이다
                .where(article.hashtag.isNotNull())
                .fetch();
    }
}
