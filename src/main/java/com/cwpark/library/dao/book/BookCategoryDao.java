package com.cwpark.library.dao.book;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookCategory;
import com.cwpark.library.repository.book.category.BookCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    public void delete(BookCategoryDto dto) {
        repository.delete(BookCategory.toEntity(dto));
    }

    public List<BookCategoryDto> findAll() {
        return repository.findAll().stream().map(BookCategoryDto::toDto).collect(Collectors.toList());
    }

    public BookCategoryDto findById(Long categoryId) {
        return BookCategoryDto.toDto(
                repository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("카테고리가 존재하지 않습니다")));

    }
}
