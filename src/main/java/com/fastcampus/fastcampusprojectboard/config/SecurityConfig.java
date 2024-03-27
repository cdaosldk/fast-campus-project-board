package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.dto.security.BoardPrincipal;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity // 필수는 아니다
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .formLogin().and()
//                .build();
        return httpSecurity
                .authorizeHttpRequests(auth -> auth // mvcMatcher를 이용한 url 설정
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().and()
                .logout()
                    .logoutSuccessUrl("/").and()
                .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() { // 아예 스프링 시큐리티에서 제외할 url 설정 -> static 리소스, css, js
//        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
//    } // 권장되지 않는 방식 ~ WARN 18800 --- [  restartedMain] o.s.s.c.a.web.builders.WebSecurity       : You are asking Spring Security to ignore org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest$StaticResourceRequestMatcher@8041add. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}