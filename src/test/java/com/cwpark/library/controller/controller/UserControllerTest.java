package com.cwpark.library.controller.controller;

import com.cwpark.library.test.annotation.WithMockCustomUser;
import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("마이 페이지")
    @WithMockCustomUser
    void myPageSuccess() throws Exception {
        UserSelectDto userSelectDto = new UserSelectDto(
                "user", "password", "name", "M", "941212", UserAuthority.USER, 0, UserOauthType.EMALE);
        given(userService.findById("user")).willReturn(userSelectDto);

        mockMvc.perform(get("/user/mypage/{userId}", "user"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/mypage"))
                .andReturn();

        verify(userService).findById("user");
    }

    @Test
    @DisplayName("마이페이지 수정")
    @WithMockCustomUser
    void updateUser() throws Exception {
        UserMyPageDto myPageDto = new UserMyPageDto("userId", "userName", "userBirth", "userSex");
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", "userId");
        param.add("userName", "userName");
        param.add("userBirth", "userBirth");
        param.add("userSex", "userSex");

        mockMvc.perform(post("/user/mypage").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/mypage/" + "userId"))
                .andReturn();

        verify(userService).updateUser(myPageDto);
    }
}