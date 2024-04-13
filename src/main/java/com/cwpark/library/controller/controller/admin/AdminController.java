package com.cwpark.library.controller.controller.admin;

import com.cwpark.library.service.book.BookCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookCategoryService bookCategoryService;

    @GetMapping("/main")
    public String mains() {
        return "/admin/main";
    }

    @GetMapping("/book/category")
    public String category() {
        return "/admin/book/category";
    }

    @GetMapping("/book/book")
    public String book(Model model) {
        model.addAttribute("category", bookCategoryService.findAll());
        return "/admin/book/book";
    }

    @GetMapping("/book/loan")
    public String loan() {
        return "/admin/book/loan";
    }

    @GetMapping("/book/reserve")
    public String reserve() {
        return "/admin/book/reserve";
    }

    @GetMapping("/user/info")
    public String info() {
        return "/admin/user/info";
    }

    @GetMapping("/user/loan")
    public String userLoan() {
        return "/admin/user/loan";
    }

    @GetMapping("/user/reserve")
    public String userReserve() {
        return "/admin/user/reserve";
    }
}
