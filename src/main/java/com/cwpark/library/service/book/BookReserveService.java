package com.cwpark.library.service.book;

import com.cwpark.library.config.aop.SameUserCheck;
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

    public List<BookReserveDto> findByReserves(String bookIsbn, BookReserveType bookReserveType) {
        BookSelectDto book = bookDao.findById(bookIsbn);

        return bookReserveDao.findByBookAndLoanReturnYn(book, bookReserveType);
    }

    public List<BookReserveDto> findByUserAndReserveStatus(String userId, BookReserveType bookReserveType) {
        UserSelectDto user = userDao.findById(userId);

        return bookReserveDao.findByUserAndReserveStatus(user, bookReserveType);
    }

    @SameUserCheck
    public String save(String userId, String bookIsbn) {
        UserSelectDto user = userDao.findById(userId);
        BookSelectDto book = bookDao.findById(bookIsbn);

        if(book.getBookLoanCnt() < book.getBookMaxLoanCnt()) {
            return "reserveLoan";
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
        reserve.setReserveStatus(BookReserveType.CANCEL);

        bookReserveDao.save(reserve);
    }

    public void delete(Long id) {
        bookReserveDao.delete(id);
    }
}
