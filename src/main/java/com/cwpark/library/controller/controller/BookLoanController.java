package com.cwpark.library.controller.controller;

import com.cwpark.library.service.UserLikeService;
import com.cwpark.library.service.book.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/loan")
public class BookLoanController {
    private final BookLoanService bookLoanService;

    @PostMapping("/save")
    public String saveLike(@RequestParam("userId") String userId, @RequestParam("bookIsbn") String bookIsbn, RedirectAttributes attributes) {
        attributes.addAttribute("msg", bookLoanService.insert(userId, bookIsbn));

        return "redirect:/book/book?bookIsbn="+bookIsbn+"&userId="+userId;
    }

}
