package com.cwpark.library.config.security;

import com.cwpark.library.data.entity.RememberMeToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, String> {
    List<RememberMeToken> findByLoginTokenUserName(String userName);
}
