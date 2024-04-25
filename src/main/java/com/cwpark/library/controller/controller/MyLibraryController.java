package com.cwpark.library.controller.controller;

import com.cwpark.library.service.book.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyLibraryController {
    private final BookLoanService bookLoanService;

    @GetMapping("/loan")
    public String loan(@RequestParam("userId") String userId, @PageableDefault(size = 10) Pageable pageable, Model model) {
        model.addAttribute("result", bookLoanService.findByUserAndLoanReturnYn(userId, "N", pageable));
        return "my/loan";
    }

    @GetMapping("/return")
    public String returns(@RequestParam("userId") String userId, @RequestParam("bookIsbn") String bookIsbn,
                          @RequestParam("loanId") Long loanId) {

        bookLoanService.loanReturn(userId, loanId, bookIsbn);
        return "redirect:/my/loan?userId=" + userId + "&page=0";
    }
}
