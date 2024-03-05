package com.cwpark.library.config.security;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        loginFailCntInit(authentication);
        resultRedirectStrategy(request, response);
    }

    @Transactional
    private void loginFailCntInit(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Account account = (Account) principal;
        User user = userRepository.findByUserId(account.getId());

        if(user.getUserLoginFailCnt() != 0) {
            user.setUserLoginFailCnt(0);
            userRepository.save(user);
        }

    }

    private void resultRedirectStrategy(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        SavedRequest savedRequest =requestCache.getRequest(request, response);

        if(savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            String defaultUrl = "/";
            redirectStrategy.sendRedirect(request, response, defaultUrl);
        }
    }

}
