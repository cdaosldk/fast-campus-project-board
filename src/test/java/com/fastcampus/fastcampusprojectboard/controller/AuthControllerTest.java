package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 인증")
@Import(SecurityConfig.class)
@WebMvcTest(Void.class) // 로그인 페이지는 컨트롤러 빈이 없으므로 void.class로 표현
public class AuthControllerTest {

    private final MockMvc mockMvc;

    // 테스트 패키지에 있으면 생성자가 하나만 있는 경우 @Autowired를 생략할 수 없다
    public AuthControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    @DisplayName("[view][GET] 게시글 리스트 페이지 - 정상 호출")
    @Test
    public void giveNothing_whenTryToLogin_thenReturnsLoginView() throws Exception {
        // given

        // when & then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                    .andDo(MockMvcResultHandlers.print());
    }
}
