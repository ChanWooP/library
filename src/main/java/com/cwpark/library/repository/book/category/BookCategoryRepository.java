package com.cwpark.library.repository.book.category;

import com.cwpark.library.data.entity.book.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, BookCategoryRepositoryCustom {

}
