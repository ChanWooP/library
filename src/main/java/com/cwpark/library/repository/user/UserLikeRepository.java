package com.cwpark.library.repository.user;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.UserLike;
import com.cwpark.library.data.entity.UserLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLikeRepository extends JpaRepository<UserLike, UserLikeId>, UserLikeCustomRepository{

}