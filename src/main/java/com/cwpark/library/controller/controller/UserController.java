package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserMyPageDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/mypage/{userId}")
    public String myPage(@PathVariable("userId") String userId, Model model) {
        UserSelectDto user = userService.findById(userId);

        if(user == null) {
            model.addAttribute("error", "wrongApproach");
            return "error/error";
        }

        model.addAttribute("user", user);

        return "/user/mypage";
    }

    @PostMapping("/join")
    public String insertUser(@Valid @ModelAttribute UserInsertDto user) {
        userService.insertUser(user);
        return "redirect:/login";
    }

    @PostMapping("/mypage")
    public String updateUser(@Valid @ModelAttribute UserMyPageDto user) {
        userService.updateUser(user);
        return "redirect:/user/mypage/" + user.getUserId();
    }

}
