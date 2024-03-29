package com.cwpark.library.integrated.controller;

import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.integrated.IntegratedController;
import com.cwpark.library.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest  extends IntegratedController {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("로그인 페이지")
    void loginView() throws Exception {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("error", "error");
        param.add("exception", "exception");
        param.add("expire", "expire");

        mockMvc.perform(get("/sign-in/login").params(param))
                .andExpect(status().isOk())
                .andExpect(view().name("/sign-in/login"))
                .andExpect(model().attribute("error", "error"))
                .andExpect(model().attribute("exception", "exception"))
                .andExpect(model().attribute("expire", "expire"))
                .andExpect(model().attribute("kakao", "https://kauth.kakao.com/oauth/authorize?client_id=ae16e6c70c9981eca7262397d6d492b6&redirect_uri=http://localhost:8080/kakao/callback&response_type=code"))
                .andReturn();
    }

    @Test
    @DisplayName("회원가입 페이지")
    void joinView() throws Exception {
        mockMvc.perform(get("/sign-in/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("/sign-in/join"))
                .andReturn();
    }

    @Test
    @DisplayName("회원가입")
    void join() throws Exception {
        UserInsertDto insertDto = new UserInsertDto(
                "userId", "userPassword", "userName", "userSex", "userBirth", null, null);
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", "userId");
        param.add("userPassword", "userPassword");
        param.add("userName", "userName");
        param.add("userSex", "userSex");
        param.add("userBirth", "userBirth");

        mockMvc.perform(post("/sign-in/join").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sign-in/login"))
                .andReturn();
    }

    @Test
    @DisplayName("비밀번호 찾기 페이지")
    void findPasswordView() throws Exception {
        mockMvc.perform(get("/sign-in/find/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("/sign-in/password"))
                .andReturn();
    }

    @Test
    @DisplayName("비밀번호 찾기 성공")
    void findPasswordSuccess() throws Exception {
        UserInsertDto insertDto = new UserInsertDto(
                "userId@cwaprk.com", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(insertDto);

        String userId = "userId@cwaprk.com";
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", userId);

        mockMvc.perform(post("/sign-in/find/password").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sign-in/login"))
                .andReturn();
    }

    @Test
    @DisplayName("비밀번호 찾기 실패")
    void findPasswordFail() throws Exception {
        String userId = "userId";
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", userId);

        mockMvc.perform(post("/sign-in/find/password").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sign-in/find/password"))
                .andExpect(model().attribute("error", "아이디가 존재하지 않습니다"))
                .andReturn();
    }
}