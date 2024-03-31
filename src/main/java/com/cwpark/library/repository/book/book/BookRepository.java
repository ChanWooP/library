package com.cwpark.library.repository.book.book;

import com.cwpark.library.data.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String>, BookRepositoryCustom {

}
