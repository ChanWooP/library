package com.cwpark.library.repository.book.hope;

import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookHope;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface BookHopeRepository extends JpaRepository<BookHope, Long>, BookHopeRepositoryCustom {
    Page<BookHope> findByUser(User user, Pageable pageable);
}