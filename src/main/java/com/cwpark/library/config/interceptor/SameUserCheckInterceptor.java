package com.cwpark.library.config.interceptor;

import com.cwpark.library.config.security.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class SameUserCheckInterceptor implements HandlerInterceptor {
    /**
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] split = request.getRequestURI().split("/");
        String pathUserId = split[split.length-1];
        String path = split[1];
        String requestId = request.getParameter("userId");

        if((requestId != null && !account.getId().equals(requestId))
            || (path.equals("api") && !account.getId().equals(pathUserId))) {

            if(account.getAuthority().get(0).equals("ADMIN")) {
                return true;
            }

            response.sendRedirect("/error/message?type=" + "NotSameUser");
            return false;
        }

        return true;
    }

}
