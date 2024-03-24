package com.cwpark.library.controller.restcontroller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("회원 삭제")
    @WithMockCustomUser
    void deleteUser() throws Exception {
        String userId = "userId";

        mockMvc.perform(delete("/api/v1/user/{userId}", userId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("Y"))
                .andReturn();

        verify(userService).deleteUser(userId);
    }
}