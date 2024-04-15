package com.cwpark.library.repository.guide.notify;

import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.enums.BookHopeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotifyRepositoryCustom {
    Page<NotifyDto> searchPage(String search, String nowDate, Pageable pageable);
    Page<NotifyDto> searchPage(String search, String frDt, String toDt, Pageable pageable);
}
