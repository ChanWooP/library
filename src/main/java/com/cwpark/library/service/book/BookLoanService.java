package com.cwpark.library.service.book;

import com.cwpark.library.dao.SettingDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.dao.book.BookLoanDao;
import com.cwpark.library.data.dto.SettingDto;
import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookLoanService {
    private final BookDao bookDao;
    private final BookLoanDao bookLoanDao;
    private final SettingDao settingDao;

    public List<BookLoanDto> findByBookAndLoanReturnYn(String bookIsbn, String loanReturnYn) {
        BookSelectDto book = bookDao.findById(bookIsbn);
        int loanDate = (int) settingDao.findById("loanDate").getTypeConversionValue();

        List<BookLoanDto> bookLoanList = bookLoanDao.findByBookAndLoanReturnYn(book, loanReturnYn);
        bookLoanList.forEach(l -> l.setLoanReturnDate(l.getLoanDate().plusDays(loanDate)));

        return bookLoanList;
    }

    public void loanReturn(Long loanId) {
        bookLoanDao.loanReturn(loanId);
    }
}
