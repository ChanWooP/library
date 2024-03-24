package com.cwpark.library.dao;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.exception.RuntimeEntityNotFoundException;
import com.cwpark.library.repository.UserRepository;
import com.cwpark.library.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserDaoTest {

    @InjectMocks
    UserDao userDao;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("아이디 중복 체크 미존재")
    void existsByIdSuccess() {
        when(userRepository.existsById("id")).thenReturn(false);

        Assertions.assertFalse(userDao.existsById("id"));

        verify(userRepository).existsById("id");
    }

    @Test
    @DisplayName("아이디 중복 체크 존재")
    void existsByIdFail() {
        when(userRepository.existsById("id")).thenReturn(true);

        Assertions.assertTrue(userDao.existsById("id"));

        verify(userRepository).existsById("id");
    }

    @Test
    @DisplayName("회원 저장")
    void insertUser() {
        User user = new User(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0 ,UserOauthType.EMALE);
        UserInsertDto insertDto = new UserInsertDto(
                "id", "password", "name", "M", "941212", UserOauthType.EMALE, UserAuthority.USER);

        when(userRepository.save(User.insertToEntity(insertDto))).thenReturn(user);

        userDao.insertUser(insertDto);

        verify(userRepository).save(User.insertToEntity(insertDto));
    }

    @Test
    @DisplayName("회원 찾기 성공")
    void findByIdSuccess() {
        User user = new User(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0 ,UserOauthType.EMALE);

        when(userRepository.findById("id")).thenReturn(Optional.of(user));

        UserSelectDto findUserDto = userDao.findById("id");

        Assertions.assertEquals(user.getUserId(), findUserDto.getUserId());

        verify(userRepository).findById("id");
    }

    @Test
    @DisplayName("회원 찾기 실패")
    void findByIdFail() {
        when(userRepository.findById("id")).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeEntityNotFoundException.class, () -> {
            userDao.findById("id");
        });

        verify(userRepository).findById("id");
    }

    @Test
    @DisplayName("회원 수정")
    void updateUser() {
        User user = new User(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0 ,UserOauthType.EMALE);
        User compUser = new User(
                "id", "password", "name", "W", "941212", UserAuthority.USER, 0 ,UserOauthType.EMALE);
        UserMyPageDto myPageDto = new UserMyPageDto("id", "name", "941212", "W");

        when(userRepository.findById("id")).thenReturn(Optional.of(user));

        userDao.updateUser(myPageDto);

        verify(userRepository).findById("id");
        verify(userRepository).save(compUser);
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteUser() {
        User user = new User(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0 ,UserOauthType.EMALE);

        when(userRepository.findById("id")).thenReturn(Optional.of(user));

        userDao.deleteUser("id");

        verify(userRepository).findById("id");
        verify(userRepository).delete(user);
    }
}