package com.cwpark.library.integrated.restcontroller.admin;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.book.BookFormDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategoryFormDto;
import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.integrated.IntegratedController;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookLoanRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    BookCategoryService bookCategoryService;
    @Autowired
    BookService bookService;
    @Autowired
    BookLoanService bookLoanService;

    @Test
    @DisplayName("대출 조회 - 유저")
    @WithMockCustomUser(userRole = "ADMIN")
    void findLoanUser() throws Exception {
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

        // 대출
        bookLoanService.insert(findUser.getUserId(), findBook.getBookIsbn());
        BookLoanDto bookLoan = bookLoanService.findByUserAndLoanReturnYn(findUser.getUserId(), "N").get(bookLoanService.findByUserAndLoanReturnYn(findUser.getUserId(), "N").size() - 1);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", findUser.getUserId());

        mockMvc.perform(get("/api/v1/admin/loan/search/user").params(param))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andReturn();

        bookLoanService.delete(bookLoan.getLoanId());
        bookService.delete(findBook.getBookIsbn());
        bookCategoryService.delete(findCategory.getCategoryId());
        userService.deleteUser(findUser.getUserId());
    }

}