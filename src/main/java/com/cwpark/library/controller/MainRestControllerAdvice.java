package com.cwpark.library.controller;

import com.cwpark.library.config.exception.RuntimeEmailException;
import com.cwpark.library.config.exception.RuntimekakaoException;
import com.cwpark.library.config.exception.RuntimeoAuthException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class MainRestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> runtimeHandle(RuntimeException e) {
        log.error("[runtimeHandle] : {}", e);

        Map<String, Object> result = new HashMap<>();
        result.put("success", "N");
        result.put("message", "서버 에러입니다");

        return ResponseEntity.ok()
                .body(result);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> entityNotFoundHandle(EntityNotFoundException e) {
        log.error("[entityNotFoundHandle] : {}", e);

        Map<String, Object> result = new HashMap<>();
        result.put("success", "N");
        result.put("message", e.getMessage());

        return ResponseEntity.ok()
                .body(result);
    }

}