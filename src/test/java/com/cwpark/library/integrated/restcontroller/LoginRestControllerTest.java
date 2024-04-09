package com.cwpark.library.integrated.restcontroller;

import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.integrated.IntegratedController;
import com.cwpark.library.service.UserService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginRestControllerTest extends IntegratedController {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("아이디 중복 확인 (중복 O)")
    @WithMockCustomUser(userName = "1@1.com")
    void existsByUserIdO() throws Exception {
        String userId = "1@1.com";
        UserInsertDto insertDto = new UserInsertDto(
                userId, "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(insertDto);

        mockMvc.perform(get("/api/v1/sign-in/join/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("N"))
                .andExpect(jsonPath("$.message").value("아이디가 중복되었습니다."))
                .andReturn();
    }

    @Test
    @DisplayName("아이디 중복 확인 (중복 X)")
    void existsByUserIdX() {
        String userId = "userId";

        ResponseEntity<Map> entity = restTemplate.getForEntity("/api/v1/sign-in/join/{userId}", Map.class, userId);
        Assertions.assertEquals(entity.getBody().get("success"), "Y");
        Assertions.assertEquals(entity.getBody().get("message"), "사용 가능한 아이디 입니다.");
    }

    @Test
    @DisplayName("아이디 찾기")
    void findByIdList() throws Exception {
        URI uri = UriComponentsBuilder
                .fromUriString(restTemplate.getRootUri())
                .path("/api/v1/sign-in/find/id")
                .queryParam("userName", "1@1.com")
                .queryParam("userBirth", "111111")
                .encode()
                .build()
                .toUri();

        ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);

        Assertions.assertNotNull(entity.getBody().get("userList"));
    }
}