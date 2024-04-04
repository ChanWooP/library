package com.cwpark.library.dao.book;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.repository.book.BookLoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookLoanDao {
    private final BookLoanRepository repository;

    public List<BookLoanDto> findByBookAndLoanReturnYn(BookSelectDto book, String loanReturnYn) {
        return repository.findByBookAndLoanReturnYn(Book.selectToEntity(book), loanReturnYn)
                .stream().map(BookLoanDto::toDto).collect(Collectors.toList());
    }
}
