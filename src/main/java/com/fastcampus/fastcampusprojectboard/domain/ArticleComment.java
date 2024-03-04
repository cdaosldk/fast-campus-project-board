package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Getter
@ToString(callSuper = true)
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
    private Article article; // 게시글

    @Setter @ManyToOne(optional = false) private UserAccount userAccount;

    @Setter
    @Column(nullable = false, length = 500)
    private String contents;

    protected ArticleComment() {
    }

    private ArticleComment(Article article, UserAccount userAccount, String contents) {
        this.article = article;
        this.userAccount = userAccount;
        this.contents = contents;
    }

    public static ArticleComment of(Article article, UserAccount userAccount, String contents) {
        return new ArticleComment(article, userAccount, contents);
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
