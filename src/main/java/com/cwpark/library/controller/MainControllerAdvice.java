package com.cwpark.library.controller;

import com.cwpark.library.config.exception.RuntimeEmailException;
import com.cwpark.library.config.exception.RuntimeIOException;
import com.cwpark.library.config.exception.RuntimekakaoException;
import com.cwpark.library.config.exception.RuntimeoAuthException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class MainControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public String runtimeHandle(RuntimeException e, Model model) {
        log.error("[runtimeHandle] : {}", e);
        model.addAttribute("message", "서버 에러입니다");

        return "error/500";
    }

    @ExceptionHandler(RuntimeoAuthException.class)
    public String oAuthHandle(RuntimeoAuthException e, Model model) {
        log.error("[oAuthExceptionHandle] : {}", e);
        model.addAttribute("message", e.getMessage());

        return "error/400";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundHandle(EntityNotFoundException e, Model model) {
        log.error("[entityNotFoundHandle] : {}", e);
        model.addAttribute("message", e.getMessage());

        return "error/400";
    }

    @ExceptionHandler(RuntimeEmailException.class)
    public String emailHandle(RuntimeEmailException e, Model model) {
        log.error("[emailHandle] : {}", e);
        model.addAttribute("message", "이메일 전송에 실패하였습니다");

        return "error/500";
    }

    @ExceptionHandler(RuntimekakaoException.class)
    public String kakaoHandle(RuntimekakaoException e, Model model) {
        log.error("[emailHandle] : {}", e);
        model.addAttribute("message", "회원가입에 실패하였습니다");

        return "error/500";
    }

    @ExceptionHandler(RuntimeIOException.class)
    public String IOEHandle(RuntimeIOException e, Model model) {
        log.error("[IOEHandle] : {}", e);
        model.addAttribute("message", "파일 업로드에 실패하였습니다");

        return "error/500";
    }

}