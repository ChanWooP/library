package com.cwpark.library.repository;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import jakarta.persistence.Inheritance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}