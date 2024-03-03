package com.cwpark.library.repository;

import com.cwpark.library.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    Boolean existsByUserId(String userId);
}
