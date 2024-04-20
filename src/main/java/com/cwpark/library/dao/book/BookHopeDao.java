package com.cwpark.library.dao.book;

import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.entity.book.BookHope;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.cwpark.library.repository.book.hope.BookHopeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookHopeDao {
    private final BookHopeRepository bookHopeRepository;

    public Page<BookHopeDto> searchPage(BookHopeStatus bookHopeStatus, String frDt, String toDt, String search, Pageable pageable) {
        return bookHopeRepository.searchPage(bookHopeStatus, frDt, toDt, search, pageable);
    }

    public void statusUpdate(Long id, BookHopeStatus status) {
        BookHope bookHope = bookHopeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("희망 도서가 존재하지 않습니다"));

        bookHope.setHopeStatus(status);
    }

    public void insert(BookHopeDto bookHopeDto) {
        bookHopeRepository.save(BookHope.toEntity(bookHopeDto));
    }

}
