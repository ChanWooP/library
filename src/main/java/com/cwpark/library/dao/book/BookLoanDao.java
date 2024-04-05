package com.cwpark.library.dao.book;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookLoan;
import com.cwpark.library.repository.book.BookLoanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookLoanDao {
    private final BookLoanRepository repository;

    public List<BookLoanDto> findByBookAndLoanReturnYn(BookSelectDto book, String loanReturnYn) {
        return repository.findByBookAndLoanReturnYn(Book.selectToEntity(book), loanReturnYn)
                .stream().map(BookLoanDto::toDto).collect(Collectors.toList());
    }

    public void loanReturn(Long loanId) {
        BookLoan findByLoan = repository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("대출이 존재하지 않습니다"));

        findByLoan.setLoanReturnDate(LocalDateTime.now());
        findByLoan.setLoanReturnYn("Y");

        repository.save(findByLoan);
    }
}
