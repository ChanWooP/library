package com.cwpark.library.controller.restcontroller;

import com.cwpark.library.controller.controller.UserController;
import com.cwpark.library.service.UserService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
class LoginRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("아이디 중복 확인 (중복 O)")
    @WithMockCustomUser
    void existsByUserIdO() throws Exception {
        String userId = "userId";
        given(userService.existsById(userId)).willReturn(true);

        mockMvc.perform(get("/api/v1/sign-in/join/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("N"))
                .andExpect(jsonPath("$.message").value("아이디가 중복되었습니다."))
                .andReturn();

        verify(userService).existsById(userId);
    }

    @Test
    @DisplayName("아이디 중복 확인 (중복 X)")
    @WithMockCustomUser
    void existsByUserIdX() throws Exception {
        String userId = "userId";
        given(userService.existsById(userId)).willReturn(false);

        mockMvc.perform(get("/api/v1/sign-in/join/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("Y"))
                .andExpect(jsonPath("$.message").value("사용 가능한 아이디 입니다."))
                .andReturn();

        verify(userService).existsById(userId);
    }
}