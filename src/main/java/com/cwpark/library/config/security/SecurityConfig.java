package com.cwpark.library.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final FailHandler failHandler;
    private final SuccessHandler successHandler;
    private final AccountService accountService;
    private final RememberMeTokenService rememberMeTokenService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DelegatingSecurityContextRepository delegatingSecurityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        http.csrf(AbstractHttpConfigurer::disable)
                .requestCache(request ->
                        request
                                .requestCache(requestCache))
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(
                                        "/", "/css/**", "/js/**", "/img/**", "/files/**",
                                        "/email/check/**", "/kakao/callback",
                                        "/sign-in/**", "/api/v1/sign-in/**"
                                ).permitAll()
                                .anyRequest().authenticated())
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/sign-in/login")
                                .loginProcessingUrl("/loginProcess")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                                .failureUrl("/login")
                                .failureHandler(failHandler)
                                .successHandler(successHandler))
                .rememberMe((rememberMe) ->
                        rememberMe
                                .key("remember")
                                .rememberMeParameter("remember")
                                .alwaysRemember(false)
                                .tokenValiditySeconds(86400*30)
                                .tokenRepository(rememberMeTokenService)
                                .userDetailsService(accountService))
                .logout((logout) ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/"))
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionFixation().changeSessionId()
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
                                .expiredUrl("/sign-in/login?expire=true"));
        return http.build();
    }
}
