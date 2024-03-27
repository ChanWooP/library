package com.cwpark.library.repository;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("아이디 찾기")
    void findId() {
        User saveUser = userRepository.save(new User("userId", "userPassword", "userName", "M", "941212",
                UserAuthority.USER, 0, UserOauthType.EMALE, "N"));

        List<User> findUser = userRepository.findByUserNameAndUserBirth("userName", "941212");

        Assertions.assertEquals(saveUser, findUser.get(0));
    }
}