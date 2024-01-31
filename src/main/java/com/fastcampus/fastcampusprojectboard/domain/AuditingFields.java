package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class) // 메타데이터 기록을 위해 필요하다
@MappedSuperclass
public abstract class AuditingFields { // 무조건 다른 클래스의 부모 클래스가 되기 떄문에

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false) private LocalDateTime createdAt;

    @CreatedBy
    @Column(nullable = false, length = 100, updatable = false) private String createdBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false) private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(nullable = false, length = 100) private String modifiedBy;

}
