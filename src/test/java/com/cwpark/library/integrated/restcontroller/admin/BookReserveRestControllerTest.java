package com.cwpark.library.integrated.restcontroller.admin;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.BookReserveDto;
import com.cwpark.library.data.dto.book.book.BookFormDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategoryFormDto;
import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.enums.BookReserveType;
import com.cwpark.library.service.UserService;
import com.cwpark.library.service.book.BookCategoryService;
import com.cwpark.library.service.book.BookLoanService;
import com.cwpark.library.service.book.BookReserveService;
import com.cwpark.library.service.book.BookService;
import com.cwpark.library.test.annotation.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookReserveRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    BookCategoryService bookCategoryService;
    @Autowired
    BookService bookService;
    @Autowired
    BookReserveService bookReserveService;

    @Test
    @DisplayName("예약 조회 - 유저")
    @WithMockCustomUser(userRole = "ADMIN")
    void findReserveUser() throws Exception {
        UserInsertDto user = new UserInsertDto(
                "test", "userPassword", "userName", "M", "951111", null, null);
        userService.insertUser(user);
        UserSelectDto findUser = userService.findById(user.getUserId());

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

        // 예약
        BookReserveDto reserve = new BookReserveDto(
                null, findBook, LocalDateTime.now(), findUser, BookReserveType.RESERVE
        );
        bookReserveService.save(reserve);
        BookReserveDto findReserve = bookReserveService.findByReserves(findBook.getBookIsbn(), BookReserveType.RESERVE).get(bookReserveService.findByReserves(findBook.getBookIsbn(), BookReserveType.RESERVE).size() - 1);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", findUser.getUserId());

        mockMvc.perform(get("/api/v1/admin/reserve/search/user").params(param))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andReturn();

        bookReserveService.delete(findReserve.getReserveId());
        bookService.delete(findBook.getBookIsbn());
        bookCategoryService.delete(findCategory.getCategoryId());
        userService.deleteUser(findUser.getUserId());
    }

}