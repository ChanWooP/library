package com.cwpark.library.dao.book;

import com.cwpark.library.data.dto.book.book.BookInsUpdDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookCategory;
import com.cwpark.library.repository.book.book.BookRepository;
import com.cwpark.library.repository.book.category.BookCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookDao {
    private final BookRepository repository;

    public void save(BookInsUpdDto dto) {
        repository.save(Book.toEntity(dto));
    }

    public Page<BookSelectDto> searchPage(String title, Pageable pageable) {
        return repository.searchPage(title, pageable);
    }

    public void delete(BookInsUpdDto dto) {
        repository.delete(Book.toEntity(dto));
    }
}
