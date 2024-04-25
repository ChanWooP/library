package com.cwpark.library.repository.book;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookLoan;
import com.cwpark.library.data.entity.book.BookReserve;
import com.cwpark.library.data.enums.BookReserveType;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface BookReserveRepository extends JpaRepository<BookReserve, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<BookReserve> findByBookAndReserveStatus(Book book, BookReserveType bookReserveType);

    List<BookReserve> findByUserAndReserveStatus(User user, BookReserveType bookReserveType);

    Optional<BookReserve> findByUserAndBookAndReserveStatus(User user, Book book, BookReserveType bookReserveType);
    Page<BookReserve> findByUserAndReserveStatus(User user, BookReserveType bookReserveType, Pageable pageable);
}
