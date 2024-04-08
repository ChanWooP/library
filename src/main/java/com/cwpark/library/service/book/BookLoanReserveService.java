package com.cwpark.library.service.book;

import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookLoanReserveService {
    private final BookDao bookDao;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void loanReserve(String bookIsbn) {
        BookSelectDto book = bookDao.findById(bookIsbn);
        book.setBookLoanCnt(book.getBookLoanCnt() - 1);
        bookDao.save(book);
    }
}
