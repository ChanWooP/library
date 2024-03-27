package com.cwpark.library.integrated.restcontroller;

import com.cwpark.library.controller.restcontroller.UserRestController;
import com.cwpark.library.integrated.IntegratedController;
import com.cwpark.library.service.UserService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserRestControllerTest extends IntegratedController {

    @Test
    @DisplayName("회원 삭제")
    @WithMockCustomUser(userName = "1@1.com")
    void deleteUser() throws Exception {
        String userId = "1@1.com";

        mockMvc.perform(delete("/api/v1/user/{userId}", userId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("Y"))
                .andReturn();
    }

    @Test
    @DisplayName("회원 삭제 실패")
    @WithMockCustomUser
    void deleteUserFail() throws Exception {
        String userId = "1@1.com";

        mockMvc.perform(delete("/api/v1/user/{userId}", userId).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/message?type=NotSameUser"))
                .andReturn();
    }

    @Test
    @DisplayName("회원 삭제 성공 ADMIN")
    @WithMockCustomUser(userRole = "ADMIN")
    void deleteUserAdminSuccess() throws Exception {
        String userId = "1@1.com";

        mockMvc.perform(delete("/api/v1/user/{userId}", userId).with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }

}