package com.cwpark.library.config;

import com.cwpark.library.config.interceptor.AdminCheckInterceptor;
import com.cwpark.library.config.interceptor.SameUserCheckInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig implements WebMvcConfigurer {
    @Value("${file.show.dir}")
    private String dir;
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

    /**
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/files/**") // 경로 설정
                .addResourceLocations(dir); // 실제 경로(window 버전)
    }
}
