package com.cwpark.library.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SameUserCheckInterceptor())
                .addPathPatterns("/user/**", "/api/v1/user/**");

        registry.addInterceptor(new AdminCheckInterceptor())
                .addPathPatterns("/admin/**", "/api/v1/admin/**");
    }
}
