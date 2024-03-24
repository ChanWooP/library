package com.cwpark.library;

import com.cwpark.library.TestDao;
import com.cwpark.library.TestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

//@SpringBootTest(classes = {TestService.class})
@ExtendWith(SpringExtension.class)
@Import({TestService.class})
class TestServiceTest {
    @MockBean
    TestDao testDao;

    @Autowired
    TestService testService;

    @Test
    public void getStringTest() {
        // getString("string") 실행 시 success 리턴 되도록 설정
        Mockito.when(testDao.getString("string"))
                .thenReturn("success");

        // success 받음
        String string = testService.getString("string");

        // 받은 값이 success 인지 확인
        Assertions.assertEquals(string, "success");

        verify(testDao).getString("string");
    }

    @Test
    public void postStringTest() {
        Mockito.when(testDao.postString("string"))
                .thenReturn("success");

        String string = testService.postString("string");

        Assertions.assertEquals(string, "success");

        verify(testDao).postString("string");
    }
}