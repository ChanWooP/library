package com.cwpark.library.service.book;

import com.cwpark.library.config.aop.SameUserCheck;
import com.cwpark.library.dao.SettingDao;
import com.cwpark.library.dao.UserDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.dao.book.BookLoanDao;
import com.cwpark.library.dao.book.BookReserveDao;
import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.BookReserveDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.enums.BookReserveType;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookReserveService {
    private final BookDao bookDao;
    private final BookReserveDao bookReserveDao;
    private final UserDao userDao;
    private final BookLoanDao bookLoanDao;
    private final SettingDao settingDao;

    public List<BookReserveDto> findByReserves(String bookIsbn, BookReserveType bookReserveType) {
        BookSelectDto book = bookDao.findById(bookIsbn);

        return bookReserveDao.findByBookAndLoanReturnYn(book, bookReserveType);
    }

    @SameUserCheck
    public List<BookReserveDto> findByUserAndReserveStatus(String userId, BookReserveType bookReserveType) {
        UserSelectDto user = userDao.findById(userId);

        return bookReserveDao.findByUserAndReserveStatus(user, bookReserveType);
    }

    @SameUserCheck
    public Page<BookReserveDto> findByUserAndReserveStatus(String userId, BookReserveType bookReserveType, Pageable pageable) {
        UserSelectDto user = userDao.findById(userId);
        return bookReserveDao.findByUserAndReserveStatus(user, bookReserveType, pageable);
    }

    @SameUserCheck
    public String save(String userId, String bookIsbn) {
        UserSelectDto user = userDao.findById(userId);
        BookSelectDto book = bookDao.findById(bookIsbn);
        int loanCnt = (int) settingDao.findById("loanCnt").getTypeConversionValue();

        if(book.getBookLoanCnt() < book.getBookMaxLoanCnt()) {
            return "reserveLoan";
        }

        if(bookLoanDao.findByUserAndLoanReturnYn(user, "N").size() +
                bookReserveDao.findByUserAndReserveStatus(user, BookReserveType.RESERVE).size() >= loanCnt) {
            return "userReserveCntOver";
        }

        if(bookReserveDao.findByUserAndBookAndReserveStatus(user, book, BookReserveType.RESERVE) != null
        || bookLoanDao.findByUserAndBookAndLoanReturnYn(user, book) != null) {
            return "reserveOverlap";
        }

        if(book.getBookReserveCnt() < book.getBookMaxReserveCnt()) {
            bookReserveDao.save(BookReserveDto.builder()
                    .book(book)
                    .user(user)
                    .reserveStatus(BookReserveType.RESERVE)
                    .reserveDate(LocalDateTime.now())
                    .build());

            book.setBookReserveCnt(book.getBookReserveCnt() + 1);
            bookDao.save(book);
            return "success";
        } else {
            return "bookReserveCntOver";
        }
    }

    @SameUserCheck
    public void cancel(String userId, Long reserveId) {
        BookReserveDto reserve = bookReserveDao.findById(reserveId);
        BookSelectDto book = bookDao.findById(reserve.getBook().getBookIsbn());

        reserve.setReserveStatus(BookReserveType.CANCEL);

        bookReserveDao.save(reserve);

        book.setBookReserveCnt(book.getBookReserveCnt() - 1);
        bookDao.save(book);
    }

    public void delete(Long id) {
        bookReserveDao.delete(id);
    }
}
