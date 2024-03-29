package com.cwpark.library.integrated.controller;

import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.integrated.IntegratedController;
import com.cwpark.library.service.UserService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends IntegratedController {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("마이 페이지")
    @WithMockCustomUser
    void myPageSuccess() throws Exception {
        UserInsertDto insertDto = new UserInsertDto(
                "user", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(insertDto);

        UserSelectDto findUser = userService.findById("user");

        mockMvc.perform(get("/user/mypage/{userId}", "user"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", findUser))
                .andExpect(view().name("/user/mypage"))
                .andReturn();
    }

    @Test
    @DisplayName("마이페이지 수정")
    @WithMockCustomUser
    void updateUser() throws Exception {
        UserInsertDto insertDto = new UserInsertDto(
                "user", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(insertDto);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", "user");
        param.add("userName", "userName");
        param.add("userBirth", "userBirth");
        param.add("userSex", "W");

        mockMvc.perform(post("/user/mypage").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/mypage/" + "user"))
                .andReturn();
    }

    @Test
    @DisplayName("마이 페이지 관리자")
    @WithMockCustomUser(userName = "admin", userRole = "ADMIN")
    void myPageAdmin() throws Exception {
        UserInsertDto insertDto = new UserInsertDto(
                "user", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(insertDto);

        UserSelectDto findUser = userService.findById("user");

        mockMvc.perform(get("/user/mypage/{userId}", "user"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", findUser))
                .andExpect(view().name("/user/mypage"))
                .andReturn();
    }
}