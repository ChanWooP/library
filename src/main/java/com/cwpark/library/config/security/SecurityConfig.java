package com.cwpark.library.config.security;

import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final FailHandler failHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(
                                        "/", "/css/**", "/js/**", "/img/**",
                                        "/login", "/user/join", "/api/user/join/**",
                                        "/email/check/**"
                                ).permitAll()
                                .anyRequest().authenticated())
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/loginProcess")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                                .failureUrl("/login")
                                .failureHandler(failHandler))
                .logout((logout) ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/"))
                .sessionManagement((sessionManagement) ->
                                sessionManagement
                                 .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
                                .expiredUrl("/login?expire=true"));
        return http.build();
    }
}
