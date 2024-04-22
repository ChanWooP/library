package com.cwpark.library.repository.book.category;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategorySearchDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCategoryRepositoryCustom {
    Page<BookCategoryDto> searchPage(Pageable pageable);

    List<BookCategorySearchDto> searchCategory();
}
