package com.cwpark.library.dao.book;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.BookReserveDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookReserve;
import com.cwpark.library.data.enums.BookReserveType;
import com.cwpark.library.repository.book.BookLoanRepository;
import com.cwpark.library.repository.book.BookReserveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookReserveDao {
    private final BookReserveRepository repository;

    public List<BookReserveDto> findByBookAndLoanReturnYn(BookSelectDto book, BookReserveType bookReserveType) {
        return repository.findByBookAndReserveStatus(Book.selectToEntity(book), bookReserveType)
                .stream().map(BookReserveDto::toDto).collect(Collectors.toList());
    }

    public void save(BookReserveDto bookReserveDto) {
        repository.save(BookReserve.toEntity(bookReserveDto));
    }

    public void delete(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("예약이 존재하지 않습니다")));
    }

    public BookReserveDto findById(Long id) {
        return BookReserveDto.toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("예약이 존재하지 않습니다")));
    }
}
