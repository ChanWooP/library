package com.cwpark.library.config.security;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
@RequiredArgsConstructor
public class FailHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = null;
        String username = request.getParameter("username");

        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다";

            // 로그인 실패 시 로그인 횟수 추가 (최대 5회 후 계정 잠김)
            userRepository.findByUserId(username).ifPresent((u) -> {
                u.setUserLoginFailCnt(u.getUserLoginFailCnt() + 1);
                userRepository.save(u);
            });

        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "내부 시스템 문제입니다 관리자에게 문의해주세요";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다";
        } else if(exception instanceof LockedException) {
            errorMessage = "로그인 가능 횟수를 초과했습니다";
        } else {
            errorMessage = "알 수 없는 오류입니다 관리자에게 문의해주세요";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/login?error=true&exception="+errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}