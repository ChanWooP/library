package com.cwpark.library.controller.controller;

import com.cwpark.library.service.guide.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final NotifyService notifyService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("notify", notifyService.findTop5ByOrderByNotifyStartDtDesc());
        return "index";
    }
}
