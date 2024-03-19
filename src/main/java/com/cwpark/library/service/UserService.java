package com.cwpark.library.service;

import com.cwpark.library.dao.UserDao;
import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.exception.ErrorException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public UserSelectDto findById(String userId) throws EntityNotFoundException {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!loginId.equals(userId)) {
            return null;
        }

        return userDao.findById(userId);
    }

    public Boolean existsById(String userId) {
        return userDao.existsById(userId);
    }

}
