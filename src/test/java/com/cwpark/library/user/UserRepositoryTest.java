package com.cwpark.library.user;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void test() {
        userRepository.save(new User("id", "password", "name", "M", "941215", UserAuthority.USER, 0, UserOauthType.EMALE));
        userRepository.save(new User("id", "password2"));
        User u = userRepository.findById("id").get();
        Assertions.assertThat(u.getUserName()).isEqualTo(null);
    }
}
