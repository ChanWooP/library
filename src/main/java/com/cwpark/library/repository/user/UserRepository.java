package com.cwpark.library.repository.user;

import com.cwpark.library.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
    List<User> findByUserNameAndUserBirth(String userName, String userBirth);
}