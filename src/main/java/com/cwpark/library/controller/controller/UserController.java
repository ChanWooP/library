package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String join() {
        return "sign-in/join";
    }

    @PostMapping("/join")
    public String insertUser(@Valid @ModelAttribute UserInsertDto user) {
        userService.insertUser(user);
        return "redirect:/sign-in/login";
    }

}
