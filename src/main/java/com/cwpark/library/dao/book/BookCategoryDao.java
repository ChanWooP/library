package com.cwpark.library.dao.book;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.entity.book.BookCategory;
import com.cwpark.library.repository.book.BookCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookCategoryDao {
    private final BookCategoryRepository repository;

    public void save(BookCategoryDto dto) {
        repository.save(BookCategory.toEntity(dto));
    }

    public Page<BookCategoryDto> searchPage(Pageable pageable) {
        return repository.searchPage(pageable);
    }

}
