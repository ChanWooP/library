package com.cwpark.library.service;

import com.cwpark.library.dao.UserDao;
import com.cwpark.library.dao.UserLikeDao;
import com.cwpark.library.dao.book.BookCategoryDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.data.dto.book.book.BookFormDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategoryFormDto;
import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserLikeDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.service.book.BookCategoryService;
import com.cwpark.library.service.book.BookService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserLikeServiceTest {
    @Autowired
    BookService bookService;

    @Autowired
    BookCategoryService bookCategoryService;

    @Autowired
    UserService userService;

    @Autowired
    UserLikeService userLikeService;

    @Test
    @DisplayName("좋아요 테스트")
    @WithMockCustomUser
    void likeTest() {
        // 유저
        UserInsertDto user = new UserInsertDto(
                "user", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(user);
        UserSelectDto findUser = userService.findById(user.getUserId());

        // 카테고리
        BookCategoryFormDto category = new BookCategoryFormDto(
                "insert", null, "categoryName"
        );
        bookCategoryService.insert(category);
        BookCategoryDto findCategory = bookCategoryService.findAll().get(bookCategoryService.findAll().size() - 1);

        // 책
        BookFormDto book = new BookFormDto(
                "insert", "isbn", findCategory.getCategoryId(), "bookTitle", "bookAuthor", "bookPublisher", "bookDistributor", "2022", "bookIndex", "bookInt",
                "bookAuthorInt", 2, 1, 5, null, null, null
        );
        bookService.insert(book);
        BookSelectDto findBook = bookService.findById(book.getBookIsbn());

        // 좋아요 +1
        userLikeService.save(findUser.getUserId(), findBook.getBookIsbn());
        UserLikeDto findUserLike = userLikeService.findById(findUser.getUserId(), findBook.getBookIsbn());
        Assertions.assertEquals(findUserLike.getUser(), findUser);

        // 좋아요 취소
        userLikeService.delete(findUserLike.getUser().getUserId(), findUserLike.getBook().getBookIsbn());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userLikeService.findById(findUser.getUserId(), findBook.getBookIsbn());
        });

        userService.deleteUser(findUser.getUserId());
        bookService.delete(findBook.getBookIsbn());
        bookCategoryService.delete(findCategory.getCategoryId());
    }

    @Test
    @DisplayName("좋아요 테스트")
    @Disabled
    void likeTest2() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch (3);

        // 유저
        UserInsertDto user = new UserInsertDto(
                "test", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(user);
        UserSelectDto findUser = userService.findById(user.getUserId());

        // 유저2
        UserInsertDto user2 = new UserInsertDto(
                "test2", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(user2);
        UserSelectDto findUser2 = userService.findById(user2.getUserId());

        // 유저3
        UserInsertDto user3 = new UserInsertDto(
                "test3", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(user3);
        UserSelectDto findUser3 = userService.findById(user3.getUserId());

        // 카테고리
        BookCategoryFormDto category = new BookCategoryFormDto(
                "insert", null, "categoryName"
        );
        bookCategoryService.insert(category);
        BookCategoryDto findCategory = bookCategoryService.findAll().get(bookCategoryService.findAll().size() - 1);

        // 책
        BookFormDto book = new BookFormDto(
                "insert", "isbn", findCategory.getCategoryId(), "bookTitle", "bookAuthor", "bookPublisher", "bookDistributor", "2022", "bookIndex", "bookInt",
                "bookAuthorInt", 2, 1, 5, null, null, null
        );
        bookService.insert(book);
        BookSelectDto findBook = bookService.findById(book.getBookIsbn());


        // 좋아요 +1
        executorService.execute(() -> {
            userLikeService.save(findUser.getUserId(), findBook.getBookIsbn());
            latch.countDown();
        });

        // 좋아요 +1
        executorService.execute(() -> {
            userLikeService.save(findUser2.getUserId(), findBook.getBookIsbn());
            latch.countDown();
        });

        // 좋아요 +1
        executorService.execute(() -> {
            userLikeService.save(findUser3.getUserId(), findBook.getBookIsbn());
            latch.countDown();
        });

        Assertions.assertEquals(bookService.findById(findBook.getBookIsbn()).getBookLike(), 3);

        userService.deleteUser(findUser.getUserId());
        userService.deleteUser(findUser2.getUserId());
        userService.deleteUser(findUser3.getUserId());
        bookService.delete(findBook.getBookIsbn());
        bookCategoryService.delete(findCategory.getCategoryId());
    }

}