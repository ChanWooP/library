package com.cwpark.library.repository.book;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookCategory;
import com.cwpark.library.data.entity.book.BookLoan;
import com.cwpark.library.data.entity.book.BookReserve;
import com.cwpark.library.data.enums.BookReserveType;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.repository.UserRepository;
import com.cwpark.library.repository.book.book.BookRepository;
import com.cwpark.library.repository.book.category.BookCategoryRepository;
import jakarta.persistence.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestRepositoryConfig.class)
class BookReserveRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookCategoryRepository bookCategoryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookReserveRepository bookReserveRepository;

    @Test
    @DisplayName("책으로 검색")
    void findByBook() {
        User saveUser1 = userRepository.save(new User("userId1", "USER_PASSWORD", "USER_NAME", "M", "951112", UserAuthority.USER, 0, UserOauthType.EMALE, "N"));
        User saveUser2 = userRepository.save(new User("userId2", "USER_PASSWORD", "USER_NAME", "M", "951112", UserAuthority.USER, 0, UserOauthType.EMALE, "N"));
        User saveUser3 = userRepository.save(new User("userId3", "USER_PASSWORD", "USER_NAME", "M", "951112", UserAuthority.USER, 0, UserOauthType.EMALE, "N"));
        User saveUser4 = userRepository.save(new User("userId4", "USER_PASSWORD", "USER_NAME", "M", "951112", UserAuthority.USER, 0, UserOauthType.EMALE, "N"));
        BookCategory saveCategory = bookCategoryRepository.save(new BookCategory(0L, "categoryName"));
        Book saveBook = bookRepository.save(new Book("bookIsbn", saveCategory, "bookTitle", "bookAuthor", "bookPublisher", "bookDistributor", "2021", "bookIndex", "bookInt", "bookAuthorInt", "bookImage", 0, 0, 0, 0, 0, 0));

        bookReserveRepository.save(new BookReserve(null, saveBook, LocalDateTime.now(), saveUser1, BookReserveType.RESERVE));
        bookReserveRepository.save(new BookReserve(null, saveBook, LocalDateTime.now(), saveUser2, BookReserveType.RESERVE));
        bookReserveRepository.save(new BookReserve(null, saveBook, LocalDateTime.now(), saveUser3, BookReserveType.RESERVE));
        bookReserveRepository.save(new BookReserve(null, saveBook, LocalDateTime.now(), saveUser4, BookReserveType.CANCEL));

        Assertions.assertEquals(3, bookReserveRepository.findByBookAndReserveStatus(saveBook, BookReserveType.RESERVE).size());
    }
}