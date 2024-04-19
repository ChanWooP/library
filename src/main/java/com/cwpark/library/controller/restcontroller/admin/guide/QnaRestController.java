package com.cwpark.library.controller.restcontroller.admin.guide;

import com.cwpark.library.data.dto.guide.qna.QnaFormDto;
import com.cwpark.library.service.guide.QnaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/qna")
public class QnaRestController {
    private final QnaService qnaService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("frDt") String frDt, @RequestParam("toDt") String toDt, @PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();

        result.put("result", qnaService.searchPage(null, frDt, toDt, pageable));

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@Valid @ModelAttribute QnaFormDto qnaFormDto) {
        Map<String, Object> result = new HashMap<>();

        qnaService.update(qnaFormDto);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestParam("id") Long id) {
        Map<String, Object> result = new HashMap<>();

        qnaService.delete(null, id);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
