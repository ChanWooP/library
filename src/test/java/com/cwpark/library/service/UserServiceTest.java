package com.cwpark.library.service;

import com.cwpark.library.annotation.WithMockCustomUser;
import com.cwpark.library.dao.UserDao;
import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.exception.RuntimeUserNotSameException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserDao userDao;
    @Mock
    PasswordEncoder pwEncoder;

    @Test
    @DisplayName("아이디 중복 체크 미존재")
    void existsByIdSuccess() {
        when(userDao.existsById("id")).thenReturn(false);

        Assertions.assertFalse(userService.existsById("id"));

        verify(userDao).existsById("id");
    }

    @Test
    @DisplayName("아이디 중복 체크 존재")
    void existsByIdFail() {
        when(userDao.existsById("id")).thenReturn(true);

        Assertions.assertTrue(userService.existsById("id"));

        verify(userDao).existsById("id");
    }

    @Test
    @DisplayName("회원 저장")
    void insertUser() {
        UserInsertDto insertDto = new UserInsertDto(
                "id", "password", "name", "M", "941212", UserOauthType.EMALE, UserAuthority.USER);

        userService.insertUser(insertDto);

        verify(userDao).insertUser(insertDto);
    }

    @Test
    @DisplayName("회원 찾기 성공")
    @WithMockCustomUser
    void findByIdSuccess() {
        UserSelectDto userSelectDto = new UserSelectDto(
                "user", "password", "name", "M", "941212", UserAuthority.USER, 0, UserOauthType.EMALE);

        when(userDao.findById("user")).thenReturn(userSelectDto);

        UserSelectDto findUser = userService.findById("user");

        Assertions.assertEquals(findUser.getUserId(), userSelectDto.getUserId());

        verify(userDao).findById("user");
    }

    @Test
    @DisplayName("회원 찾기 실패")
    @WithMockCustomUser
    void findByIdFail() {
        // 기본 name = user임
        UserSelectDto userSelectDto = new UserSelectDto(
                "user1", "password", "name", "M", "941212", UserAuthority.USER, 0, UserOauthType.EMALE);

        when(userDao.findById("user1")).thenReturn(userSelectDto);

        Assertions.assertThrows(RuntimeUserNotSameException.class, () -> {
            userService.findById("user1");
            verify(userDao).findById("user1");
        });
    }

    @Test
    @DisplayName("회원 수정")
    void updateUser() {
        UserMyPageDto myPageDto = new UserMyPageDto("id", "name", "941212", "W");

        userService.updateUser(myPageDto);

        verify(userDao).updateUser(myPageDto);
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteUser() {
        userService.deleteUser("id");

        verify(userDao).deleteUser("id");
    }
}