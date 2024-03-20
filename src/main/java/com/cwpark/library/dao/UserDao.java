package com.cwpark.library.dao;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.exception.RuntimeEntityNotFoundException;
import com.cwpark.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    public void insertUser(UserInsertDto user) {
        userRepository.save(User.insertToEntity(user));
    }

    public void updateUser(UserMyPageDto user) {
        userRepository.findById(user.getUserId()).ifPresent((u) -> {
            u.setUserName(user.getUserName());
            u.setUserBirth(user.getUserBirth());
            u.setUserSex(user.getUserSex());
            userRepository.save(u);
        });
    }

    public UserSelectDto findById(String userId){
        try {
            User findUser = userRepository.findById(userId)
                    .orElseThrow(EntityNotFoundException::new);

            return UserSelectDto.toDto(findUser);
        } catch (EntityNotFoundException e) {
            throw new RuntimeEntityNotFoundException("아이디가 존재하지 않습니다.");
        }
    }

    public Boolean existsById(String userId) {
        return userRepository.existsById(userId);
    }

}
