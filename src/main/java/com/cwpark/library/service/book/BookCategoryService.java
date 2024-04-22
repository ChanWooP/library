package com.cwpark.library.service.book;

import com.cwpark.library.dao.book.BookCategoryDao;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategoryFormDto;
import com.cwpark.library.data.dto.book.category.BookCategoryInsUpdDto;
import com.cwpark.library.data.dto.book.category.BookCategorySearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCategoryService {
    private final BookCategoryDao dao;

    public void insert(BookCategoryFormDto dto) {
        BookCategoryInsUpdDto category = BookCategoryInsUpdDto.toDto(dto);
        dao.save(category);
    }

    public void update(BookCategoryFormDto dto) {
        BookCategoryDto findCategory = dao.findById(dto.getCategoryId());
        findCategory.setCategoryName(dto.getCategoryName());

        dao.save(BookCategoryInsUpdDto.selectToDto(findCategory));
    }

    public Page<BookCategoryDto> searchPage(Pageable pageable) {
        return dao.searchPage(pageable);
    }

    public void delete(Long bookCategoryId) {
        BookCategoryDto findCategory = dao.findById(bookCategoryId);
        dao.delete(findCategory);
    }

    public List<BookCategorySearchDto> searchCategory() {
        return dao.searchCategory();
    }

    public List<BookCategoryDto> findAll() {
        return dao.findAll();
    }
}
