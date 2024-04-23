package com.cwpark.library.service;

import com.cwpark.library.config.aop.SameUserCheck;
import com.cwpark.library.dao.UserDao;
import com.cwpark.library.dao.UserLikeDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLikeService {
    private final UserLikeDao userLikeDao;
    private final UserDao userDao;
    private final BookDao bookDao;

    public Page<UserLikeDto> searchPage(Pageable pageable, String userId) {
        return userLikeDao.searchPage(pageable, userId);
    }

    public UserLikeDto findById(String userId, String bookIsbn) {
        return userLikeDao.findById(userId, bookIsbn);
    }

    public boolean existsById(String userId, String bookIsbn) {
        return userLikeDao.existsById(userId, bookIsbn);
    }

    @SameUserCheck
    public boolean save(String userId, String bookIsbn) {
        BookSelectDto book = bookDao.findById(bookIsbn);
        book.setBookLike(book.getBookLike() + 1);

        if (existsById(userId, bookIsbn)) {
            return false;
        }

        userLikeDao.save(
                UserLikeDto.builder()
                .user(userDao.findById(userId))
                .book(book)
                .build()
        );

        bookDao.save(book);

        return true;
    }

    @SameUserCheck
    public void delete(String userId, String bookIsbn) {
        BookSelectDto book = bookDao.findById(bookIsbn);
        book.setBookLike(book.getBookLike() - 1);

        userLikeDao.delete(
                UserLikeDto.builder()
                        .user(userDao.findById(userId))
                        .book(bookDao.findById(bookIsbn))
                        .build()
        );

        bookDao.save(book);
    }

}
