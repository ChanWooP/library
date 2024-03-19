package com.cwpark.library.user;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.repository.UserRepository;
import com.cwpark.library.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        UserInsertDto user = new UserInsertDto(
                "id", "password", "name", "M", "941212", UserOauthType.EMALE,UserAuthority.USER);

        // 저장
        userService.insertUser(user);
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void joinTest() {
        // 조회
        UserSelectDto findUser = userService.findById("id");

        Assertions.assertThat("id").isEqualTo(findUser.getUserId());
    }

    @Test
    @DisplayName("찾는 아이디가 없는 경우")
    void findUserByIdTest() {
        // 조회
        Assertions.assertThatThrownBy(() -> userService.findById("test"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("아이디 중복 체크")
    void existsByUserIdTest() {
        // 조회
        Assertions.assertThat(userService.existsById("id")).isTrue();
    }

    @Test
    @DisplayName("회원 정보 수정")
    void userUpdateTest() {
        UserMyPageDto user = new UserMyPageDto("id", "name", "971019", "M");
        userService.updateUser(user);

        UserSelectDto findUser = userService.findById(user.getUserId());

        // 조회
        Assertions.assertThat(findUser.getUserBirth()).isEqualTo("971019");
    }

}
