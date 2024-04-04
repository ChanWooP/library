package com.cwpark.library.repository.book;

import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookLoan;
import com.cwpark.library.data.entity.book.BookReserve;
import com.cwpark.library.data.enums.BookReserveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReserveRepository extends JpaRepository<BookReserve, Long> {
    List<BookReserve> findByBookAndReserveStatus(Book book, BookReserveType bookReserveType);
}
