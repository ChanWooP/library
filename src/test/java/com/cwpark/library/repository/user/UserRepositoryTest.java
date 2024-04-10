package com.cwpark.library.repository.user;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestRepositoryConfig.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("아이디 찾기")
    void findId() {
        User saveUser = userRepository.save(new User("userId", "userPassword", "userName", "M", "941213",
                UserAuthority.USER, 0, UserOauthType.EMALE, "N"));

        List<User> findUser = userRepository.findByUserNameAndUserBirth("userName", "941213");

        Assertions.assertEquals(saveUser.getUserId(), findUser.get(0).getUserId());
    }

    @Test
    @DisplayName("사용자 조회")
    void findUser() {
        User saveUser = userRepository.save(new User("userId", "userPassword", "userName", "M", "941213",
                UserAuthority.USER, 0, UserOauthType.EMALE, "N"));

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserSelectDto> users = userRepository.searchPage("userId", pageRequest);

        Assertions.assertEquals(users.getContent().size(), 1);
    }
}