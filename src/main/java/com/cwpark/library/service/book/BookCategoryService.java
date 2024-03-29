package com.cwpark.library.service.book;

import com.cwpark.library.dao.book.BookCategoryDao;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCategoryService {
    private final BookCategoryDao dao;

    public void save(BookCategoryDto dto) {
        dao.save(dto);
    }

    public Page<BookCategoryDto> searchPage(Pageable pageable) {
        return dao.searchPage(pageable);
    }
}
