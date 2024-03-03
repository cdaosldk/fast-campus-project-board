package com.fastcampus.fastcampusprojectboard.dto.response;

import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String contents,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) implements Serializable {

    public static ArticleResponse of(Long id, String title, String contents, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, contents, hashtag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.contents(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }

}