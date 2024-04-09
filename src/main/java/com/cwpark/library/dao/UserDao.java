package com.cwpark.library.dao;

import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserMyPageDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("아이디가 존재하지 않습니다"));

        return UserSelectDto.toDto(findUser);
    }

    public List<UserSelectDto> findById(String userName, String userBirth){
        return userRepository.findByUserNameAndUserBirth(userName, userBirth)
                .stream().map(UserSelectDto::toDto).collect(Collectors.toList());
    }

    public void updateUser(UserMyPageDto user) {
        User findUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다"));

        findUser.setUserName(user.getUserName());
        findUser.setUserBirth(user.getUserBirth());
        findUser.setUserSex(user.getUserSex());
    }

    public void deleteUser(String userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("아이디가 존재하지 않습니다"));

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
