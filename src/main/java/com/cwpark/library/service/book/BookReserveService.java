package com.cwpark.library.service.book;

import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.dao.book.BookLoanDao;
import com.cwpark.library.dao.book.BookReserveDao;
import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.BookReserveDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.enums.BookReserveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookReserveService {
    private final BookDao bookDao;
    private final BookReserveDao bookReserveDao;

    public List<BookReserveDto> findByBookAndLoanReturnYn(String bookIsbn, BookReserveType bookReserveType) {
        BookSelectDto book = bookDao.findById(bookIsbn);

        return bookReserveDao.findByBookAndLoanReturnYn(book, bookReserveType);
    }
}
