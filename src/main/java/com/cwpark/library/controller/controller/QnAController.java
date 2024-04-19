package com.cwpark.library.controller.controller;

import com.cwpark.library.data.dto.guide.qna.QnaFormDto;
import com.cwpark.library.service.guide.NotifyService;
import com.cwpark.library.service.guide.QnaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnAController {
    private final QnaService qnaService;

    @GetMapping("/search")
    public String search(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("result", qnaService.searchPage(pageable));
        return "guide/qna/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute QnaFormDto qnaFormDto) {
        qnaService.insert(qnaFormDto.getUserId(), qnaFormDto);
        return "redirect:/qna/search";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("userId") String userId, @RequestParam("id") Long id) {
        qnaService.delete(userId, id);
        return "redirect:/qna/search";
    }
}
