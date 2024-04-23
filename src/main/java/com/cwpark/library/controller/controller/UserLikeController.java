package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.book.BookHopeFormDto;
import com.cwpark.library.service.UserLikeService;
import com.cwpark.library.service.book.BookHopeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class UserLikeController {
    private final UserLikeService userLikeService;

    @PostMapping("/save")
    public String saveLike(@RequestParam("userId") String userId, @RequestParam("bookIsbn") String bookIsbn, RedirectAttributes attributes) {
        if(!userLikeService.save(userId, bookIsbn)) {
            attributes.addAttribute("overlap", true);
        }

        return "redirect:/book/book?bookIsbn="+bookIsbn+"&userId="+userId;
    }

    @DeleteMapping("/delete")
    public String deleteLike(@RequestParam("userId") String userId, @RequestParam("bookIsbn") String bookIsbn) {
        userLikeService.delete(userId, bookIsbn);
        return "redirect:/book/book?bookIsbn="+bookIsbn+"&userId="+userId;
    }

}
