package com.cwpark.library.service.book;

import com.cwpark.library.config.aop.SameUserCheck;
import com.cwpark.library.dao.SettingDao;
import com.cwpark.library.dao.UserDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.dao.book.BookLoanDao;
import com.cwpark.library.dao.book.BookReserveDao;
import com.cwpark.library.data.dto.SettingDto;
import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.BookReserveDto;
import com.cwpark.library.data.dto.book.book.BookInsUpdDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.book.BookLoan;
import com.cwpark.library.data.enums.BookReserveType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookLoanService {
    private final BookDao bookDao;
    private final BookReserveDao bookReserveDao;
    private final BookLoanDao bookLoanDao;
    private final SettingDao settingDao;
    private final UserDao userDao;

    public List<BookLoanDto> findByBookAndLoanReturnYn(String bookIsbn, String loanReturnYn) {
        BookSelectDto book = bookDao.findById(bookIsbn);
        int loanDate = (int) settingDao.findById("loanDate").getTypeConversionValue();

        List<BookLoanDto> bookLoanList = bookLoanDao.findByBookAndLoanReturnYn(book, loanReturnYn);
        bookLoanList.forEach(l -> l.setLoanReturnDate(l.getLoanDate().plusDays(loanDate)));

        return bookLoanList;
    }

    public List<BookLoanDto> findByUserAndLoanReturnYn(String userId, String loanReturnYn) {
        UserSelectDto user = userDao.findById(userId);
        int loanDate = (int) settingDao.findById("loanDate").getTypeConversionValue();

        List<BookLoanDto> bookLoanList = bookLoanDao.findByUserAndLoanReturnYn(user, loanReturnYn);
        bookLoanList.forEach(l -> l.setLoanReturnDate(l.getLoanDate().plusDays(loanDate)));

        return bookLoanList;
    }

    @SameUserCheck
    public Page<BookLoanDto> findByUserAndLoanReturnYn(String userId, String loanReturnYn, Pageable pageable) {
        UserSelectDto user = userDao.findById(userId);
        int loanDate = (int) settingDao.findById("loanDate").getTypeConversionValue();

        Page<BookLoanDto> page = bookLoanDao.findByUserAndLoanReturnYn(User.selectToEntity(user), loanReturnYn, pageable);
        page.forEach(l -> l.setLoanReturnDate(l.getLoanDate().plusDays(loanDate)));

        return page;
    }

    @SameUserCheck
    public void loanReturn(String userId, Long id, String bookIsbn) {
        bookLoanDao.loanReturn(id);

        BookSelectDto book = bookDao.findById(bookIsbn);

        List<BookReserveDto> reserves = bookReserveDao.findByBookAndLoanReturnYn(book, BookReserveType.RESERVE);

        if (!reserves.isEmpty()) {
            reserves.get(0).setReserveStatus(BookReserveType.LOAN);
            bookReserveDao.save(reserves.get(0));

            bookLoanDao.save(BookLoanDto.builder()
                    .loanId(null)
                    .book(book)
                    .loanDate(LocalDateTime.now())
                    .loanReturnDate(null)
                    .user(userDao.findById(reserves.get(0).getUser().getUserId()))
                    .loanReturnYn("N")
                    .build());

            book.setBookReserveCnt(book.getBookReserveCnt() - 1);
        } else {
            book.setBookLoanCnt(book.getBookLoanCnt() - 1);
        }
        bookDao.save(book);
    }

    public void loanReturnBatch(Long id, String bookIsbn) {
        bookLoanDao.loanReturn(id);

        BookSelectDto book = bookDao.findById(bookIsbn);

        List<BookReserveDto> reserves = bookReserveDao.findByBookAndLoanReturnYn(book, BookReserveType.RESERVE);

        if (!reserves.isEmpty()) {
            reserves.get(0).setReserveStatus(BookReserveType.LOAN);
            bookReserveDao.save(reserves.get(0));

            bookLoanDao.save(BookLoanDto.builder()
                    .loanId(null)
                    .book(book)
                    .loanDate(LocalDateTime.now())
                    .loanReturnDate(null)
                    .user(userDao.findById(reserves.get(0).getUser().getUserId()))
                    .loanReturnYn("N")
                    .build());

            book.setBookReserveCnt(book.getBookReserveCnt() - 1);
        } else {
            book.setBookLoanCnt(book.getBookLoanCnt() - 1);
        }
        bookDao.save(book);
    }

    @SameUserCheck
    public String insert(String userId, String bookIsbn) {
        BookSelectDto book = bookDao.findById(bookIsbn);
        UserSelectDto user = userDao.findById(userId);
        int loanCnt = (int) settingDao.findById("loanCnt").getTypeConversionValue();

        if(findByUserAndLoanReturnYn(userId, "N").size() +
                bookReserveDao.findByUserAndReserveStatus(user, BookReserveType.RESERVE).size() >= loanCnt) {
            return "userLoanCntOver";
        }

        if(bookLoanDao.findByUserAndBookAndLoanReturnYn(user, book) != null
        || bookReserveDao.findByUserAndBookAndReserveStatus(user, book, BookReserveType.RESERVE) != null) {
            return "loanOverlap";
        }

        if(book.getBookReserveCnt() <= 0 && book.getBookLoanCnt() < book.getBookMaxLoanCnt()) {
            bookLoanDao.save(BookLoanDto.builder()
                    .loanId(null)
                    .book(book)
                    .loanDate(LocalDateTime.now())
                    .loanReturnDate(null)
                    .user(user)
                    .loanReturnYn("N")
                    .build());

            book.setBookLoanCnt(book.getBookLoanCnt() + 1);
            bookDao.save(book);
            return "success";
        } else {
            return "bookLoanCntOver";
        }
    }

    public BookLoanDto findById(Long id) {
        return bookLoanDao.findById(id);
    }

    public void delete(Long id) {
        bookLoanDao.delete(id);
    }

    public List<BookLoanDto> findByLoanDateLessThanEqualAndLoanReturnYn(LocalDateTime localDateTime) {
        return bookLoanDao.findByLoanDateLessThanEqualAndLoanReturnYn(localDateTime);
    }
}
