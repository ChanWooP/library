package com.cwpark.library.repository.book.book;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategorySearchDto;
import com.cwpark.library.data.entity.book.BookCategory;
import com.cwpark.library.repository.book.category.BookCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestRepositoryConfig.class)
@Slf4j
class BookCategoryRepositoryTest {
    @Autowired
    BookCategoryRepository bookCategoryRepository;

    @Test
    @DisplayName("카테고리 추가")
    void insert() {
        BookCategory category = new BookCategory(null, "name");
        BookCategory saveCategory = bookCategoryRepository.save(category);

        Assertions.assertEquals(category, saveCategory);
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void list() {
        BookCategory category = new BookCategory(null, "name");
        BookCategory saveCategory1 = bookCategoryRepository.save(category);
        BookCategory saveCategory2 = bookCategoryRepository.save(category);
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<BookCategoryDto> page = bookCategoryRepository.searchPage(pageRequest);

        Assertions.assertEquals(page.getSize(), 2);
    }

    @Test
    @DisplayName("카테고리 검색 조회")
    void count() {
        BookCategory category = new BookCategory(null, "name");
        BookCategory saveCategory1 = bookCategoryRepository.save(category);

        List<BookCategorySearchDto> list = bookCategoryRepository.searchCategory();

        Assertions.assertFalse(list.isEmpty());
        log.info("list : {}", list.get(0).getCategoryCount());
    }
}