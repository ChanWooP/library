package com.cwpark.library.service.book;

import com.cwpark.library.dao.SettingDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.dao.book.BookLoanDao;
import com.cwpark.library.dao.book.BookReserveDao;
import com.cwpark.library.data.dto.SettingDto;
import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.BookReserveDto;
import com.cwpark.library.data.dto.book.book.BookInsUpdDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.BookLoan;
import com.cwpark.library.data.enums.BookReserveType;
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
    private final BookReserveDao bookReserveDao;
    private final BookLoanDao bookLoanDao;
    private final SettingDao settingDao;

    public List<BookLoanDto> findByBookAndLoanReturnYn(String bookIsbn, String loanReturnYn) {
        BookSelectDto book = bookDao.findById(bookIsbn);
        int loanDate = (int) settingDao.findById("loanDate").getTypeConversionValue();

        List<BookLoanDto> bookLoanList = bookLoanDao.findByBookAndLoanReturnYn(book, loanReturnYn);
        bookLoanList.forEach(l -> l.setLoanReturnDate(l.getLoanDate().plusDays(loanDate)));

        return bookLoanList;
    }

    public void loanReturn(Long id, String bookIsbn) {
        bookLoanDao.loanReturn(id);

        BookSelectDto book = bookDao.findById(bookIsbn);
        List<BookReserveDto> reserves = bookReserveDao.findByBookAndLoanReturnYn(book, BookReserveType.RESERVE);

        if (!reserves.isEmpty()) {
            reserves.get(0).setReserveStatus(BookReserveType.LOAN);
            bookReserveDao.save(reserves.get(0));

            insert(book, reserves.get(0).getUser());

            book.setBookReserveCnt(book.getBookReserveCnt() - 1);
        } else {
            book.setBookLoanCnt(book.getBookLoanCnt() - 1);
        }
        bookDao.save(book);
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

    public BookLoanDto findById(Long id) {
        return bookLoanDao.findById(id);
    }

    public void delete(Long id) {
        bookLoanDao.delete(id);
    }
}
