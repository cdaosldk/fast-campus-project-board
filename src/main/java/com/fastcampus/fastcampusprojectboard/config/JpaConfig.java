package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
//        return () -> Optional.of("admin"); // 스프링 시큐리티 적용 전 생성, 수정자를 고정

        // SecurityContextHolder 인증정보를 가지고 있는 클래스
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
//                .map(x -> (BoardPrincipal) x) // 람다 이용 형변환
                .map(BoardPrincipal.class::cast)
                .map(BoardPrincipal::getUsername);
    }
}
