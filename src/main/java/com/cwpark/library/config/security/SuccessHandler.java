package com.cwpark.library.config.security;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
        Object principal = authentication.getPrincipal();
        Account account = (Account) principal;
        User user = userRepository.findById(account.getId()).orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다"));

        request.getSession().setMaxInactiveInterval(3600);
        loginFailCntInit(user);
        resultRedirectStrategy(request, response, user);
    }

    private void loginFailCntInit(User user) {
        if(user.getUserLoginFailCnt() != 0) {
            user.setUserLoginFailCnt(0);
            userRepository.save(user);
        }
    }

    private void resultRedirectStrategy(HttpServletRequest request,HttpServletResponse response, User user) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(user.getUserFindPasswordYn().equals("Y")) {
            redirectStrategy.sendRedirect(request, response, "/sign-in/change/password");
        } else {
            if(savedRequest != null) {
                String targetUrl = savedRequest.getRedirectUrl();
                redirectStrategy.sendRedirect(request, response, targetUrl);
            } else {
                String defaultUrl = "/";
                redirectStrategy.sendRedirect(request, response, defaultUrl);
            }
        }
    }

}
