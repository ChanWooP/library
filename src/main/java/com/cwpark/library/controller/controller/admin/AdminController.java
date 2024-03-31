package com.cwpark.library.controller.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/main")
    public String mains() {
        return "/admin/main";
    }

    @GetMapping("/book/category")
    public String category() {
        return "/admin/book/category";
    }
}
