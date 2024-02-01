package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
//@EntityListeners(AuditingEntityListener.class) // 메타데이터 기록을 위해 필요하다
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 도메인에서 사용자가 수정이 가능한 내용에만 setter를 사용한다
    // 본문 검색의 경우, mysql은 풀 텍스트 서치를 지원하고, 그렇지 않아도 엘라스틱 서치 등을 이용한다
    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false, length = 10000) private String contents;

    @Setter private String hashtag;

    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @Setter
    // toString이 모든 컬럼에 적용하기 위해 다 조회할 때, articleComment -> article -> articleComment .... ~ 제외해야한다
    @ToString.Exclude
    private Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // 메타데이터 공통부분 추출 ~ 클래스를 만들고 필드를 공통 메타데이터로 한다
    // 1) 임베디드
    // 2) mapped-superclass
//    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
//    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;
//    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;
//    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;

    protected Article() {
    }

    // PK와 메타데이터는 제외하고 도메인에 관련있는 정보만 초기화 가능한 생성자를 만든다
    private Article(String title, String contents, String hashtag) {
        this.title = title;
        this.contents = contents;
        this.hashtag = hashtag;
    }

    // 팩토리 메서드
    public static Article of(String title, String contents, String hashtag) {
        return new Article(title, contents, hashtag);
    }

    // 리스트로 게시판 데이터를 다룰 때 equals & hashcode를 사용 ~ @Entity에서는 id만 비교해도 충분하다
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        // 데이터베이스에 인서트하지 않은(영속화하지 않은) id의 경우는 null수도 있기 떄문에 null 유효성 체크
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
