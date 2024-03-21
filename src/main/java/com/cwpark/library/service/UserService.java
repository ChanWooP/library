package com.cwpark.library.service;

import com.cwpark.library.config.security.Account;
import com.cwpark.library.dao.UserDao;
import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.exception.RuntimeEntityNotFoundException;
import com.cwpark.library.exception.RuntimeUserNotSameException;
import com.cwpark.library.exception.RuntimeoAuthException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder pwEncoder;

    public void insertUser(UserInsertDto user) {
        user.setUserPassword(pwEncoder.encode(user.getUserPassword()));
        userDao.insertUser(user);
    }

    public void updateUser(UserMyPageDto user) {
        userDao.updateUser(user);
    }

    public UserSelectDto findByIdToKakao(String userId) {

        UserSelectDto user = userDao.findById(userId);

        if(!user.getUserOauthType().equals(UserOauthType.KAKAO)) {
            throw new RuntimeoAuthException("다른 경로로 회원가입이 되어있습니다");
        }

        return user;
    }

    public UserSelectDto findById(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();

        if(!account.getAuthority().get(0).equals("ADMIN") && !account.getId().equals(userId)) {
            throw new RuntimeUserNotSameException("접근이 불가능합니다.");
        }

        return userDao.findById(userId);
    }

    public Boolean existsById(String userId) {
        return userDao.existsById(userId);
    }

    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }

}
