package com.cwpark.library.controller.controller;

import com.cwpark.library.TestController;
import com.cwpark.library.annotation.WithMockCustomUser;
import com.cwpark.library.config.security.SecurityConfig;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.exception.RuntimeUserNotSameException;
import com.cwpark.library.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 페이지")
    @WithMockCustomUser
    void join() throws Exception {
        mockMvc.perform(get("/user/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("/sign-in/join"))
                .andReturn();
    }

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
}