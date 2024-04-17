package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.user.UserMyPageDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage/{userId}")
    public String myPage(@PathVariable("userId") String userId, Model model) {
        UserSelectDto user = userService.findById(userId);

        model.addAttribute("user", user);

        return "user/mypage";
    }

    @PostMapping("/mypage")
    public String myPage(@Valid @ModelAttribute UserMyPageDto user) {
        userService.updateUser(user);
        return "redirect:/user/mypage/" + user.getUserId();
    }

    @PostMapping("/change/password")
    public String changePasswordPost(@RequestParam("userId") String userId, @RequestParam("userPassword") String userPassword) {
        userService.updateChangePassword(userId, userPassword);
        return "redirect:/";
    }

}
