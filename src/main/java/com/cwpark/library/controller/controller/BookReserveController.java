package com.cwpark.library.controller.controller;

import com.cwpark.library.service.book.BookLoanService;
import com.cwpark.library.service.book.BookReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class BookReserveController {
    private final BookReserveService bookReserveService;

    @PostMapping("/save")
    public String saveLike(@RequestParam("userId") String userId, @RequestParam("bookIsbn") String bookIsbn, RedirectAttributes attributes) {
        attributes.addAttribute("msg2", bookReserveService.save(userId, bookIsbn));
        return "redirect:/book/book?bookIsbn="+bookIsbn+"&userId="+userId;
    }

}
