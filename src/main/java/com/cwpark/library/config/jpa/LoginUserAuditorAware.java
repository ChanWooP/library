package com.cwpark.library.config.jpa;

import com.cwpark.library.config.security.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginUserAuditorAware implements AuditorAware<String> {

    /**
     * @return
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = null;
        String userId = null;


        if(authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("system");
        }

        // 익명사용자의 경우 타입이 String으로 들어오기 때문에 조건 지정
        if(authentication.getPrincipal() instanceof String) {
            userId = "system";
        } else {
            account = (Account) authentication.getPrincipal();
            userId = account.getId();
        }

        return Optional.of(userId);
    }
}
