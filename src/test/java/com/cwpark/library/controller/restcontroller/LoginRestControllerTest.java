package com.cwpark.library.controller.restcontroller;

import com.cwpark.library.controller.controller.UserController;
import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.service.UserService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("아이디 찾기")
    @WithMockCustomUser
    void findById() throws Exception {
        UserSelectDto userSelectDto = new UserSelectDto(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0, UserOauthType.KAKAO);
        List<UserSelectDto> list = new ArrayList<>();
        list.add(userSelectDto);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userName", "name");
        param.add("userBirth", "941212");

        given(userService.findById("name", "941212")).willReturn(list);

        mockMvc.perform(get("/api/v1/sign-in/find/id").params(param))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userList[0]").value("id"))
                .andReturn();

        verify(userService).findById("name", "941212");
    }
}