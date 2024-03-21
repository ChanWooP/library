package com.cwpark.library.user;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.exception.RuntimeUserNotSameException;
import com.cwpark.library.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    SecurityContextRepository securityContextRepository;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Test
    @DisplayName("로그인 페이지 확인")
    void loginPageTest() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입")
    void userJoinTest() throws Exception {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", "userId");
        param.add("userPassword", "userPassword");
        param.add("userName", "userName");
        param.add("userSex", "userSex");
        param.add("userBirth", "userBirth");

        mvc.perform(post("/user/join").params(param))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andReturn();
    }

    @Test
    @DisplayName("내 정보 테스트")
    void userGetTest() throws Exception {
        UserInsertDto user = new UserInsertDto(
                "test", "password", "name", "M", "941212", UserOauthType.EMALE,UserAuthority.USER);
        // 저장
        userService.insertUser(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                "test", "password"
        );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);


        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);

        mvc.perform(get("/user/mypage/{userId}", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/mypage"))
                .andReturn();
    }

    @Test
    @DisplayName("회원 탈퇴")
    void userDeleteTest() throws Exception {
        UserInsertDto user = new UserInsertDto(
                "test", "password", "name", "M", "941212", UserOauthType.EMALE,UserAuthority.USER);
        // 저장
        userService.insertUser(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                "test", "password"
        );


        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);


        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);

        mvc.perform(delete("/user/mypage/{userId}", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andReturn();
    }
}
