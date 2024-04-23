package com.cwpark.library.dao;

import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserLikeDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.UserLike;
import com.cwpark.library.data.entity.UserLikeId;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.repository.user.UserLikeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLikeDao {
    private final UserLikeRepository userLikeRepository;

    public Page<UserLikeDto> searchPage(Pageable pageable, String userId) {
        return userLikeRepository.searchPage(pageable, userId);
    }

    public UserLikeDto findById(String userId, String bookIsbn) {
        UserLike userLike = userLikeRepository.findById(new UserLikeId(userId, bookIsbn))
                .orElseThrow(() -> new EntityNotFoundException("좋아요가 존재하지 않습니다"));

        return UserLikeDto.toDto(userLike);
    }

    public boolean existsById(String userId, String bookIsbn) {
        return userLikeRepository.existsById(new UserLikeId(userId, bookIsbn));
    }

    public void save(UserLikeDto userLikeDto) {
        userLikeRepository.save(UserLike.toEntity(userLikeDto));
    }

    public void delete(UserLikeDto userLikeDto) {
        userLikeRepository.delete(UserLike.toEntity(userLikeDto));
    }
}
