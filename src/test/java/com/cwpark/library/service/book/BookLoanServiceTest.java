package com.cwpark.library.service.book;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.BookReserveDto;
import com.cwpark.library.data.dto.book.book.BookFormDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategoryFormDto;
import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.BookReserve;
import com.cwpark.library.data.enums.BookReserveType;
import com.cwpark.library.service.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookLoanServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    BookCategoryService bookCategoryService;
    @Autowired
    BookService bookService;
    @Autowired
    BookLoanService bookLoanService;
    @Autowired
    BookReserveService bookReserveService;


    @Test
    @DisplayName("책 반납")
    void loanReturn() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch (2);

        // 대출중인 유저1
        UserInsertDto user = new UserInsertDto(
                "test", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(user);
        UserSelectDto findUser = userService.findById(user.getUserId());

        // 예약중인 유저
        UserInsertDto user2 = new UserInsertDto(
                "test2", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(user2);
        UserSelectDto findUser2 = userService.findById(user2.getUserId());

        // 대출중인 유저2
        UserInsertDto user3 = new UserInsertDto(
                "test3", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(user3);
        UserSelectDto findUser3 = userService.findById(user3.getUserId());

        // 카테고리
        BookCategoryFormDto category = new BookCategoryFormDto(
                "insert", null, "categoryName"
        );
        bookCategoryService.insert(category);
        BookCategoryDto findCategory = bookCategoryService.findAll().get(bookCategoryService.findAll().size() - 1);

        // 책
        BookFormDto book = new BookFormDto(
                "insert", "isbn", findCategory.getCategoryId(), "bookTitle", "bookAuthor", "bookPublisher", "bookDistributor", "2022", "bookIndex", "bookInt",
                "bookAuthorInt", 2, 1, 5, null, null, null
        );
        bookService.insert(book);
        BookSelectDto findBook = bookService.findById(book.getBookIsbn());
        findBook.setBookReserveCnt(1);
        findBook.setBookLoanCnt(2);
        bookService.likeLoanReserve(findBook);

        // 대출 2건
        bookLoanService.insert(findBook, findUser);
        BookLoanDto bookLoan = bookLoanService.findByBookAndLoanReturnYn(findBook.getBookIsbn(), "N").get(bookLoanService.findByBookAndLoanReturnYn(findBook.getBookIsbn(), "N").size() - 1);
        bookLoanService.insert(findBook, findUser3);
        BookLoanDto bookLoan2 = bookLoanService.findByBookAndLoanReturnYn(findBook.getBookIsbn(), "N").get(bookLoanService.findByBookAndLoanReturnYn(findBook.getBookIsbn(), "N").size() - 1);

        // 예약 1건
        BookReserveDto reserve = new BookReserveDto(
                null, findBook, LocalDateTime.now(), findUser2, BookReserveType.RESERVE
        );
        bookReserveService.save(reserve);
        BookReserveDto findReserve = bookReserveService.findByReserves(findBook.getBookIsbn(), BookReserveType.RESERVE).get(bookReserveService.findByReserves(findBook.getBookIsbn(), BookReserveType.RESERVE).size() - 1);

        // 기존 대출 반납 후 예약 건 대출 진행
        executorService.execute(() -> {
            bookLoanService.loanReturn(bookLoan.getLoanId(), "isbn");
            latch.countDown();
        });

        executorService.execute(() -> {
            bookLoanService.loanReturn(bookLoan2.getLoanId(), "isbn");
            latch.countDown();
        });

        latch.await();

        // 최종 대출 및 예약 건수 조회
        findBook = bookService.findById(book.getBookIsbn());

        bookLoanService.delete(bookLoan.getLoanId());
        bookLoanService.delete(bookLoan2.getLoanId());
        bookReserveService.delete(findReserve.getReserveId());
        bookLoanService.delete(bookLoanService.findByBookAndLoanReturnYn(findBook.getBookIsbn(), "N").get(bookLoanService.findByBookAndLoanReturnYn(findBook.getBookIsbn(), "N").size() - 1).getLoanId());
        bookService.delete(findBook.getBookIsbn());
        bookCategoryService.delete(findCategory.getCategoryId());
        userService.deleteUser(findUser.getUserId());
        userService.deleteUser(findUser2.getUserId());
        userService.deleteUser(findUser3.getUserId());

        Assertions.assertEquals(findBook.getBookLoanCnt(), 1);
        Assertions.assertEquals(findBook.getBookReserveCnt(), 0);
    }
}