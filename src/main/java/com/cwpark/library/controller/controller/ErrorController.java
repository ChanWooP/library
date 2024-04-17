package com.cwpark.library.controller.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/error/message")
    public String message(HttpServletRequest request, Model model) {
        if(request.getParameter("type").equals("NotSameUser")
        || request.getParameter("type").equals("NotAdmin")) {
            model.addAttribute("message", "접근이 불가능합니다");
        }

        return "error/message";
    }
}
