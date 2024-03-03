package com.cwpark.library.email;

import com.cwpark.library.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    @DisplayName("이메일 인증 확인")
    void emailTest() {
        try {
            String result = emailService.sendSimpleMessage("cksdn4002@gmail.com");
            Assertions.assertThat(result).isNotNull();
        } catch (Exception e) {
            log.info("errror={}", e.getMessage(), e);
        }

    }
}
