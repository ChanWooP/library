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
        userRepository.save(User.builder()
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .userName(user.getUserName())
                .userSex(user.getUserSex())
                .userBirth(user.getUserBirth())
                .userAuthority(UserAuthority.USER)
                .userLoginFailCnt(0)
                .userOauthType(UserOauthType.EMALE)
                .build()
        );
    }

    public UserUpdateDto findUserId(String userId) throws EntityNotFoundException{
        User findUser = userRepository.findByUserId(userId);

        if(findUser == null) {
            throw new EntityNotFoundException("아이디가 존재하지 않습니다.");
        }

        return UserUpdateDto.builder()
                .userId(findUser.getUserId())
                .userPassword(findUser.getUserPassword())
                .userName(findUser.getUserName())
                .userSex(findUser.getUserSex())
                .userBirth(findUser.getUserBirth())
                .userAuthority(findUser.getUserAuthority())
                .userLoginFailCnt(findUser.getUserLoginFailCnt())
                .userOauthType(findUser.getUserOauthType())
                .build();
    }

    public Boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

}
