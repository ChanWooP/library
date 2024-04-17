package com.cwpark.library.controller.controller;

import com.cwpark.library.service.guide.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notify")
public class NotifyController {
    private final NotifyService notifyService;

    @GetMapping("/search")
    public String search(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("result", notifyService.searchPage(LocalDate.now().toString().replaceAll("-", ""), null, pageable));
        return "guide/notify/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam("id") Long id) {
        model.addAttribute("result", notifyService.findById(id));
        return "guide/notify/detail";
    }
}
