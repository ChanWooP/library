package com.cwpark.library.user;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserUpdateDto;
import com.cwpark.library.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원 가입 테스트")
    void joinTest() {
        UserInsertDto user = new UserInsertDto(
                "id", "password", "name", "M", "941212");

        // 저장
        userService.insertUser(user);

        // 조회
        UserUpdateDto findUser = userService.findUserId(user.getUserId());

        Assertions.assertThat(user.getUserId()).isEqualTo(findUser.getUserId());
    }

    @Test
    @DisplayName("찾는 아이디가 없는 경우")
    void findUserByIdTest() {
        // 조회
        Assertions.assertThatThrownBy(() -> userService.findUserId("test"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("아이디 중복 체크")
    void existsByUserIdTest() {
        UserInsertDto user = new UserInsertDto(
                "id", "password", "name", "M", "941212");

        userService.insertUser(user);

        // 조회
        Assertions.assertThat(userService.existsByUserId(user.getUserId())).isTrue();
    }

}
