package com.cwpark.library.dao;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.config.exception.RuntimeEntityNotFoundException;
import com.cwpark.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    public Boolean existsById(String userId) {
        return userRepository.existsById(userId);
    }

    public void insertUser(UserInsertDto user) {
        userRepository.save(User.insertToEntity(user));
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

    public void updateUser(UserMyPageDto user) {
        userRepository.findById(user.getUserId()).ifPresent((u) -> {
            u.setUserName(user.getUserName());
            u.setUserBirth(user.getUserBirth());
            u.setUserSex(user.getUserSex());
            userRepository.save(u);
        });
    }

    public void deleteUser(String userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.delete(findUser);
    }

    public void updatePassword(String userId, String password, String yN) {
        userRepository.findById(userId).ifPresent((u) -> {
            u.setUserPassword(password);
            u.setUserFindPasswordYn(yN);
            userRepository.save(u);
        });
    }

}
