package com.fastcampus.fastcampusprojectboard.dto;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.fastcampus.fastcampusprojectboard.domain.Article}
 */
public record ArticleDto(
        LocalDateTime createdAt,
        String createdBy,
        String title,
        String contents,
        String hashtag
) {
    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String contents, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, contents, hashtag);
    }
}