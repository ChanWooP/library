package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import com.cwpark.library.config.oauth.KakaoService;
import com.cwpark.library.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest(LoginController.class)
@MockBean(JpaMetamodelMappingContext.class)
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    KakaoService kakaoService;
    @MockBean
    UserService userService;

    @Test
    @DisplayName("로그인 페이지")
    @WithMockCustomUser
    void loginView() throws Exception {
        given(kakaoService.getKakaoLogin()).willReturn("kakao");

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("error", "error");
        param.add("exception", "exception");
        param.add("expire", "expire");

        mockMvc.perform(get("/sign-in/login").params(param))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in/login"))
                .andExpect(model().attribute("error", "error"))
                .andExpect(model().attribute("exception", "exception"))
                .andExpect(model().attribute("expire", "expire"))
                .andExpect(model().attribute("kakao", "kakao"))
                .andReturn();

        verify(kakaoService).getKakaoLogin();
    }

    @Test
    @DisplayName("회원가입 페이지")
    @WithMockCustomUser
    void joinView() throws Exception {
        mockMvc.perform(get("/sign-in/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in/join"))
                .andReturn();
    }

    @Test
    @DisplayName("회원가입")
    @WithMockCustomUser
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

        verify(userService).insertUser(insertDto);
    }

    @Test
    @DisplayName("비밀번호 찾기 페이지")
    @WithMockCustomUser
    void findPasswordView() throws Exception {
        mockMvc.perform(get("/sign-in/find/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in/password"))
                .andReturn();
    }

    @Test
    @DisplayName("비밀번호 찾기 성공")
    @WithMockCustomUser
    void findPasswordSuccess() throws Exception {
        String userId = "userId";
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", userId);

        given(userService.updateFindPassword(userId)).willReturn(true);

        mockMvc.perform(post("/sign-in/find/password").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sign-in/login"))
                .andReturn();

        verify(userService).updateFindPassword(userId);
    }

    @Test
    @DisplayName("비밀번호 찾기 실패")
    @WithMockCustomUser
    void findPasswordFail() throws Exception {
        String userId = "userId";
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", userId);

        given(userService.updateFindPassword(userId)).willReturn(false);

        mockMvc.perform(post("/sign-in/find/password").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sign-in/find/password"))
                .andExpect(model().attribute("error", "아이디가 존재하지 않습니다"))
                .andReturn();

        verify(userService).updateFindPassword(userId);
    }
}