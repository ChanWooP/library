package com.cwpark.library.repository.book.book;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookCategory;
import com.cwpark.library.repository.book.category.BookCategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestRepositoryConfig.class)
class BookRepositoryTest {

    @Autowired
    BookCategoryRepository bookCategoryRepository;
    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("책 추가")
    void insert() {
        BookCategory bookCategory = new BookCategory(null, "test");
        Book book = new Book("isbn", bookCategory, "제목", "작가", "출판사", "유통사", "출판년도", "목차", "책소개", "작가소개",
        "책대표이미지", 5, 5, 0, 0, 0);

        bookCategoryRepository.save(bookCategory);
        Book saveBook = bookRepository.save(book);

        Assertions.assertEquals(book, saveBook);
    }

    @Test
    @DisplayName("책 목록 조회")
    void list() {
        BookCategory bookCategory = new BookCategory(null, "test");

        Book book1 = new Book("isbn1", bookCategory, "제목", "작가", "출판사", "유통사", "출판년도", "목차", "책소개", "작가소개",
                "책대표이미지", 5, 5, 0, 0, 0);
        Book book2 = new Book("isbn2", bookCategory, "제목", "작가", "출판사", "유통사", "출판년도", "목차", "책소개", "작가소개",
                "책대표이미지", 5, 5, 0, 0, 0);

        bookCategoryRepository.save(bookCategory);
        bookRepository.save(book1);
        bookRepository.save(book2);

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<BookSelectDto> page = bookRepository.searchPage("isbn", pageRequest);

        Assertions.assertEquals(page.getSize(), 2);
    }

}