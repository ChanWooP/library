package com.cwpark.library.service;

import com.cwpark.library.config.email.EmailService;
import com.cwpark.library.dao.UserDao;
import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserMyPageDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.config.exception.RuntimeoAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder pwEncoder;
    private final EmailService emailService;

    public Boolean existsById(String userId) {
        return userDao.existsById(userId);
    }

    public void insertUser(UserInsertDto user) {
        user.setUserPassword(pwEncoder.encode(user.getUserPassword()));
        userDao.insertUser(user);
    }

    public UserSelectDto findById(String userId) {
        return userDao.findById(userId);
    }

    public List<UserSelectDto> findById(String userName, String userBirth) {
        return userDao.findById(userName, userBirth);
    }

    public void updateUser(UserMyPageDto user) {
        userDao.updateUser(user);
    }

    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }

    public UserSelectDto findByIdToKakao(String userId) {

        UserSelectDto user = userDao.findById(userId);

        if(!user.getUserOauthType().equals(UserOauthType.KAKAO)) {
            throw new RuntimeoAuthException("다른 경로로 회원가입이 되어있습니다");
        }

        return user;
    }

    public Boolean updateFindPassword(String userId) {
        if(userDao.existsById(userId)) {
            String ePw = emailService.sendSimplePassword(userId);
            userDao.updatePassword(userId, pwEncoder.encode(ePw), "Y");
            return true;
        } else {
            return false;
        }
    }

    public void updateChangePassword(String userId, String password) {
        userDao.updatePassword(userId, pwEncoder.encode(password), "N");
    }

}
