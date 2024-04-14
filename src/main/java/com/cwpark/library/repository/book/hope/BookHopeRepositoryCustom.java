package com.cwpark.library.repository.book.hope;

import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.enums.BookHopeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookHopeRepositoryCustom {
    Page<BookHopeDto> searchPage(BookHopeStatus bookHopeStatus, String frDt, String toDt, String search, Pageable pageable);
}
