package com.cwpark.library.dao.book;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookLoan;
import com.cwpark.library.repository.book.BookLoanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<BookLoanDto> findByUserAndLoanReturnYn(UserSelectDto user, String loanReturnYn) {
        return repository.findByUserAndLoanReturnYn(User.selectToEntity(user), loanReturnYn)
                .stream().map(BookLoanDto::toDto).collect(Collectors.toList());
    }

    public BookLoanDto findByUserAndBookAndLoanReturnYn(UserSelectDto user, BookSelectDto book) {
        BookLoan bookLoan = repository.findByUserAndBookAndLoanReturnYn(User.selectToEntity(user), Book.selectToEntity(book), "N")
                .orElse(null);

        return bookLoan != null ? BookLoanDto.toDto(bookLoan) : null;
    }

    public void loanReturn(Long loanId) {
        BookLoan findByLoan = repository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("대출이 존재하지 않습니다"));

        findByLoan.setLoanReturnDate(LocalDateTime.now());
        findByLoan.setLoanReturnYn("Y");

        repository.save(findByLoan);
    }

    public void save(BookLoanDto bookLoanDto) {
        repository.save(BookLoan.toEntity(bookLoanDto));
    }

    public BookLoanDto findById(Long id) {
        BookLoan bookLoan = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("예약이 존재하지 않습니다"));
        return BookLoanDto.toDto(bookLoan);
    }

    public void delete(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("예약이 존재하지 않습니다")));
    }

    public Page<BookLoanDto> findByUserAndLoanReturnYn(User user, String loanReturnYn, Pageable pageable) {
        return repository.findByUserAndLoanReturnYn(user, loanReturnYn, pageable).map(BookLoanDto::toDto);
    }
}
