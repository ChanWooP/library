package com.cwpark.library.service;

import com.cwpark.library.config.email.EmailService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import com.cwpark.library.dao.UserDao;
import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserMyPageDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.config.exception.RuntimeoAuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserDao userDao;
    @Mock
    EmailService emailService;
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

    @Test
    @DisplayName("카카오 회원가입 여부 성공")
    void findByIdToKakaoSuccess() {
        UserSelectDto userSelectDto = new UserSelectDto(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0, UserOauthType.KAKAO);
        when(userDao.findById("id")).thenReturn(userSelectDto);

        userService.findByIdToKakao("id");

        verify(userDao).findById("id");
    }

    @Test
    @DisplayName("카카오 회원가입 여부 실패")
    void findByIdToKakaoFail() {
        UserSelectDto userSelectDto = new UserSelectDto(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0, UserOauthType.EMALE);
        when(userDao.findById("id")).thenReturn(userSelectDto);

        Assertions.assertThrows(RuntimeoAuthException.class, () -> {
            userService.findByIdToKakao("id");
        });

        verify(userDao).findById("id");
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    void updatePasswordSuccess() throws Exception {
        when(userDao.existsById("id")).thenReturn(true);
        when(emailService.sendSimplePassword("id")).thenReturn("12345678");
        when(pwEncoder.encode("12345678")).thenReturn("!@#");

        Assertions.assertTrue(userService.updateFindPassword("id"));

        verify(userDao).existsById("id");
        verify(emailService).sendSimplePassword("id");
        verify(pwEncoder).encode("12345678");
        verify(userDao).updatePassword("id", "!@#", "Y");
    }

    @Test
    @DisplayName("비밀번호 변경 실패")
    void updatePasswordFail() throws Exception {
        when(userDao.existsById("id")).thenReturn(false);

        Assertions.assertFalse(userService.updateFindPassword("id"));

        verify(userDao).existsById("id");
    }

    @Test
    @DisplayName("비밀번호 로그인 변경")
    void updateChangePassword() {
        when(pwEncoder.encode("password")).thenReturn("!@#");

        userService.updateChangePassword("id", "password");

        verify(userDao).updatePassword("id", "!@#", "N");
    }

    @Test
    @DisplayName("회원 List 찾기")
    @WithMockCustomUser
    void findByIdList() {
        UserSelectDto userSelectDto = new UserSelectDto(
                "user", "password", "name", "M", "941212", UserAuthority.USER, 0, UserOauthType.EMALE);

        List<UserSelectDto> list = new ArrayList<>();
        list.add(userSelectDto);

        when(userDao.findById("name", "941212")).thenReturn(list);

        List<UserSelectDto> user = userService.findById("name", "941212");

        Assertions.assertEquals(user.get(0).getUserId(), userSelectDto.getUserId());

        verify(userDao).findById("name", "941212");
    }


}