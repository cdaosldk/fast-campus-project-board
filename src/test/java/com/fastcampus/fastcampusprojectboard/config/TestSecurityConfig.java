package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    // 모킹 필수
    @MockBean private UserAccountRepository userAccountRepository;


    @BeforeTestMethod // 스프링 테스트를 할 때만 인증을 사용할 수 있다
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(
                Optional.of(
                    UserAccount.of(
                        "unoTest",
                        "pw",
                        "uno-test@gmail.com",
                        "uno-test",
                        "test-memo"
                )
        ));
    }
}
