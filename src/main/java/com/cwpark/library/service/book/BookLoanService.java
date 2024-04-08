package com.cwpark.library.service.book;

import com.cwpark.library.dao.SettingDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.dao.book.BookLoanDao;
import com.cwpark.library.data.dto.SettingDto;
import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.book.BookInsUpdDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.BookLoan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public void loanReturn(Long id) {
        bookLoanDao.loanReturn(id);
    }

    public void insert(BookSelectDto bookSelectDto, UserSelectDto userSelectDto) {
        bookLoanDao.save(BookLoanDto.builder()
                .loanId(null)
                .book(bookSelectDto)
                .loanDate(LocalDateTime.now())
                .loanReturnDate(null)
                .user(userSelectDto)
                .loanReturnYn("N")
                .build());
    }
}
