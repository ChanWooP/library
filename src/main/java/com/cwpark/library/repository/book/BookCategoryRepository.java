package com.cwpark.library.repository.book;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.book.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, BookCategoryRepositoryCustom {

}
