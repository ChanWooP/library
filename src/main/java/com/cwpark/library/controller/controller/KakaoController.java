package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.exception.RuntimeoAuthException;
import com.cwpark.library.service.KakaoService;
import com.cwpark.library.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {
    private final KakaoService kakaoService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Value("${kakao.default.password}")
    private String KAKAO_PASSWORD;

    @ExceptionHandler(RuntimeoAuthException.class)
    public String oAuthExHandle(RuntimeoAuthException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "/error/5xx";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String accessToken = kakaoService.getAccessToken(code);
        UserInsertDto userInsertDto = kakaoService.getUserInfo(accessToken);
        UserSelectDto kakaoUser = null;

        if(!userService.existsById(userInsertDto.getUserId())) {
            userService.insertUser(userInsertDto);
        } else {
            kakaoUser = userService.findByIdToKakao(userInsertDto.getUserId());
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                kakaoUser == null ? userInsertDto.getUserId() : kakaoUser.getUserId(),
                KAKAO_PASSWORD
        );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);

        return "redirect:/";
    }
}
