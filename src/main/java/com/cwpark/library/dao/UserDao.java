package com.cwpark.library.dao;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserUpdateDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    public void insertUser(UserInsertDto user) {
        userRepository.save(User.insertToEntity(user));
    }

    public UserUpdateDto findUserId(String userId) throws EntityNotFoundException{
        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("아이디가 존재하지 않습니다."));

        return UserUpdateDto.toDto(findUser);
    }

    public Boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

}
