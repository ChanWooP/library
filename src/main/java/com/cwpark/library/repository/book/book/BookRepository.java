package com.cwpark.library.repository.book.book;

import com.cwpark.library.data.entity.book.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String>, BookRepositoryCustom {
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Book> findById(String id);
}