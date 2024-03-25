package com.cwpark.library.integrated.restcontroller;

import com.cwpark.library.controller.restcontroller.UserRestController;
import com.cwpark.library.integrated.IntegratedController;
import com.cwpark.library.service.UserService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestControllerTest extends IntegratedController {

    @Test
    @DisplayName("회원 삭제")
    @WithMockCustomUser
    void deleteUser() throws Exception {
        String userId = "1@1.com";

        mockMvc.perform(delete("/api/v1/user/{userId}", userId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("Y"))
                .andReturn();
    }
}