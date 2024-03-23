package com.cwpark.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestController.class)
@MockBean(JpaMetamodelMappingContext.class) // JPA를 사용하는 경우 넣어줘야 함
class TestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TestService testService;

    @Test
    @DisplayName("GET 테스트")
    @WithMockUser // 스프링 시큐리티 인증 권한 부여
    void getStringTest() throws Exception {
        // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
        // 예) getString() 메소드에 파라미터 "string"가 들어갔을 때 "success"가 리턴되어야 한다는 것을 의미(실제 실행은 안함)
        given(testService.getString("string")).willReturn("success");

        String string = "string";

        mockMvc.perform(get("/test/" + string))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("Y"))
                .andDo(print());

        // verify : 해당 객체의 메소드가 실행되었는지 체크
        verify(testService).getString("string");
    }

    @Test
    @DisplayName("POST 테스트")
    @WithMockUser
    void postStringTest() throws Exception {
        given(testService.postString("string")).willReturn("success");

        Map<String, Object> map = new HashMap<>();
        map.put("string", "string");

        String json = new ObjectMapper().writeValueAsString(map);

        mockMvc.perform(post("/test")
                        .content(json) // 넘길 값 지정
                        .contentType(MediaType.APPLICATION_JSON) // 넘길 값의 타입 지정
                        .with(csrf())) // 스프링 시큐리티 csrf 토큰 생성
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("Y"))
                .andDo(print());

        verify(testService).postString("string");
    }

    @Test
    @DisplayName("해당 테스트를 실행하지 않겠다")
    @Disabled
    void disable() {
    }

}