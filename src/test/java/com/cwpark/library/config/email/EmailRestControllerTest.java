package com.cwpark.library.config.email;

import com.cwpark.library.annotation.WithMockCustomUser;
import com.cwpark.library.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmailRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
class EmailRestControllerTest {

    @MockBean
    EmailService emailService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("이메일 전송 테스트")
    @WithMockCustomUser
    void emailConfirm() throws Exception {
        given(emailService.sendSimpleMessage("1@1.com")).willReturn("12345678");

        mockMvc.perform(get("/email/check/{userEmail}", "1@1.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.check").value("12345678"));

    }
}