package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.book.BookHopeFormDto;
import com.cwpark.library.service.book.BookHopeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hope")
public class HopeController {
    private final BookHopeService bookHopeService;

    @GetMapping("/add")
    public String getHope() {
        return "book/hope";
    }

    @PostMapping("/add")
    public String postHope(@Valid @ModelAttribute BookHopeFormDto dto) {
        bookHopeService.insert(dto);
        return "redirect:/hope/add";
    }
}
