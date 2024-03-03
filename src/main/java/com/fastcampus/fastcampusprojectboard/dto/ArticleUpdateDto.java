package com.fastcampus.fastcampusprojectboard.dto;

/**
 * DTO for {@link com.fastcampus.fastcampusprojectboard.domain.Article}
 */
public record ArticleUpdateDto(
        String title,
        String contents,
        String hashtag
) { // JPA Buddy 사용시 Serializable 옵션 선택 체크할 것
    public static ArticleUpdateDto of(String title, String contents, String hashtag) {
        return new ArticleUpdateDto(title, contents, hashtag);
    }
}