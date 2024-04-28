package com.cwpark.library.repository.book;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookLoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    List<BookLoan> findByBookAndLoanReturnYn(Book book, String loanReturnYn);
    List<BookLoan> findByUserAndLoanReturnYn(User user, String loanReturnYn);
    Optional<BookLoan> findByUserAndBookAndLoanReturnYn(User user, Book book, String loanReturnYn);
    Page<BookLoan> findByUserAndLoanReturnYn(User user, String loanReturnYn, Pageable pageable);
    List<BookLoan> findByLoanDateLessThanEqualAndLoanReturnYn(LocalDateTime loanDate, String loanReturnYn);
}
