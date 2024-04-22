package com.cwpark.library.repository.book.book;

import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.entity.book.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BookSelectDto> searchPage(String title, Pageable pageable);
    Page<BookSelectDto> searchBook(Long category, String search, Pageable pageable);
}
