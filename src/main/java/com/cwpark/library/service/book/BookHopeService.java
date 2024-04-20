package com.cwpark.library.service.book;

import com.cwpark.library.dao.UserDao;
import com.cwpark.library.dao.book.BookHopeDao;
import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.dto.book.BookHopeFormDto;
import com.cwpark.library.data.enums.BookHopeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class BookHopeService {
    private final BookHopeDao bookHopeDao;
    private final UserDao userDao;

    public Page<BookHopeDto> searchPage(BookHopeStatus bookHopeStatus, String frDt, String toDt, String search, Pageable pageable) {
        return bookHopeDao.searchPage(bookHopeStatus, frDt, toDt, search, pageable);
    }

    public void statusUpdate(Long id, BookHopeStatus status) {
        bookHopeDao.statusUpdate(id, status);
    }

    public void insert(BookHopeFormDto dto) {
        dto.setUser(userDao.findById(dto.getUserId()));
        dto.setHopeDate(LocalDate.now().toString().replaceAll("-", ""));
        dto.setHopeStatus(BookHopeStatus.REQUEST);

        bookHopeDao.insert(BookHopeDto.formToDto(dto));
    }
}
