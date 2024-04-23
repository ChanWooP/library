package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.book.BookHopeFormDto;
import com.cwpark.library.service.UserLikeService;
import com.cwpark.library.service.book.BookCategoryService;
import com.cwpark.library.service.book.BookHopeService;
import com.cwpark.library.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final BookCategoryService bookCategoryService;
    private final UserLikeService userLikeService;

    @GetMapping("/category")
    public String getCategory(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam(value = "category", required = false) Long category, @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        model.addAttribute("category", bookCategoryService.searchCategory());
        model.addAttribute("book", bookService.searchBook(category, search, pageable));
        return "book/category";
    }

    @GetMapping("/book")
    public String getBook(Model model, @RequestParam("bookIsbn") String bookIsbn, @RequestParam("userId") String userId) {
        model.addAttribute("result", bookService.findById(bookIsbn));
        model.addAttribute("like", userLikeService.existsById(userId ,bookIsbn));
        return "book/book";
    }
}
