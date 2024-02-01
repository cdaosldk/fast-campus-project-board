package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "contents"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class) // 메타데이터 기록을 위해 필요하다
@Entity
public class ArticleComment extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @Setter
    @Column(nullable = false, length = 500)
    private String contents;

//    @CreatedDate
//    @Column(nullable = false) private LocalDateTime createdAt;
//    @CreatedBy
//    @Column(nullable = false, length = 100) private String createdBy;
//    @LastModifiedDate
//    @Column(nullable = false) private LocalDateTime modifiedAt;
//    @LastModifiedBy
//    @Column(nullable = false, length = 100) private String modifiedBy;

    protected ArticleComment() {
    }

    private ArticleComment(Article article, String contents) {
        this.article = article;
        this.contents = contents;
    }

    public static ArticleComment of(Article article, String contents) {
        return new ArticleComment(article, contents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleComment that = (ArticleComment) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
