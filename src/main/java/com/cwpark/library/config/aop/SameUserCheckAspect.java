package com.cwpark.library.config.aop;

import com.cwpark.library.config.exception.RuntimeNotSameUserException;
import com.cwpark.library.config.security.Account;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SameUserCheckAspect {
    @Around("@annotation(com.cwpark.library.config.aop.SameUserCheck) && args(userId, ..)")
    public Object doSameUserCheck(ProceedingJoinPoint joinPoint, String userId) throws Throwable {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal.equals("anonymousUser")) {
            throw new RuntimeNotSameUserException("로그인을 진행해주세요");
        } else {
            Account account = (Account) principal;

            if(!account.getAuthority().get(0).equals("ADMIN") && !account.getId().equals(userId)) {
                throw new RuntimeNotSameUserException("아이디가 같지 않습니다");
            }
        }

        return joinPoint.proceed();
    }
}
