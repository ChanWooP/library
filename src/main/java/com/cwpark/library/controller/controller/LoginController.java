package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.config.oauth.KakaoService;
import com.cwpark.library.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class LoginController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @ExceptionHandler(MessagingException.class)
    public String messagingHandle(MessagingException e, Model model) {
        model.addAttribute("message", "이메일 전송에 실패하였습니다");
        return "/error/5xx";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error
                        ,@RequestParam(value = "exception", required = false) String exception
                        ,@RequestParam(value = "expire", required = false) String expire
                        , Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        model.addAttribute("expire", expire);
        model.addAttribute("kakao", kakaoService.getKakaoLogin());

        return "/sign-in/login";
    }

    @GetMapping("/join")
    public String join() {
        return "/sign-in/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute UserInsertDto user) {
        userService.insertUser(user);
        return "redirect:/sign-in/login";
    }

    @GetMapping("/find/password")
    public String findPassword() {
        return "/sign-in/password";
    }

    @PostMapping("/find/password")
    public String findPassword(@RequestParam("userId") String userId, RedirectAttributes redirectAttributes) throws Exception {
        if(!userService.updateFindPassword(userId)) {
            redirectAttributes.addAttribute("error", "아이디가 존재하지 않습니다");
            return "redirect:/sign-in/find/password";
        }

        return "redirect:/sign-in/login";
    }

    @GetMapping("/find/id")
    public String findId() {
        return "/sign-in/id";
    }

}
