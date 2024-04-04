package com.cwpark.library.service.book;

import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.dao.book.BookLoanDao;
import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookLoanService {
    private final BookDao bookDao;
    private final BookLoanDao bookLoanDao;

    public List<BookLoanDto> findByBookAndLoanReturnYn(String bookIsbn, String loanReturnYn) {
        BookSelectDto book = bookDao.findById(bookIsbn);

        return bookLoanDao.findByBookAndLoanReturnYn(book, loanReturnYn);
    }
}
