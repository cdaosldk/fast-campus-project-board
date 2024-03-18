package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        String userId,
        String userPassword,
        String email,
        String nickname,
        String memo,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    // 레포지토리 레이어에 저장될 때는 JPA Auditiong으로 기록한 메타데이터가 없으므로, null로도 생성 가능한 팩토리 메서드 추가
    public static UserAccountDto of(String userId,
                                    String userPassword,
                                    String email,
                                    String nickname,
                                    String memo) {
        return new UserAccountDto(
                userId,
                userPassword,
                email,
                nickname,
                memo,
                null,
                null,
                null,
                null);
    }
    public static UserAccountDto of(String userId,
                                    String userPassword,
                                    String email,
                                    String nickname,
                                    String memo,
                                    LocalDateTime createdAt,
                                    String createdBy,
                                    LocalDateTime modifiedAt,
                                    String modifiedBy) {
        return new UserAccountDto(
                userId,
                userPassword,
                email,
                nickname,
                memo,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo
        );
    }

}