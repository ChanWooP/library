package com.cwpark.library.controller.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error
                        ,@RequestParam(value = "exception", required = false) String exception
                        ,@RequestParam(value = "expire", required = false) String expire
                        , Model model
                        , HttpServletRequest request) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        model.addAttribute("expire", expire);

        return "sign-in/login";
    }

}
