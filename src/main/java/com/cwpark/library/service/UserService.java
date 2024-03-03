package com.cwpark.library.service;

import com.cwpark.library.dao.UserDao;
import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public UserUpdateDto findUserId(String userId) throws EntityNotFoundException {
        return userDao.findUserId(userId);
    }

    public Boolean existsByUserId(String userId) {
        return userDao.existsByUserId(userId);
    }

}
