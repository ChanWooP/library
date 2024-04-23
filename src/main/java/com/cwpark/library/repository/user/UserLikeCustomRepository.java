package com.cwpark.library.repository.user;

import com.cwpark.library.data.dto.user.UserLikeDto;
import com.cwpark.library.data.entity.UserLike;
import com.cwpark.library.data.entity.UserLikeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeCustomRepository {
    Page<UserLikeDto> searchPage(Pageable pageable, String userId);
}