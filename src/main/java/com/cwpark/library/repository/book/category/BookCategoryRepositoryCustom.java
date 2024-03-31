package com.cwpark.library.repository.book.category;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface BookCategoryRepositoryCustom {
    Page<BookCategoryDto> searchPage(Pageable pageable);
}
