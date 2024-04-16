package com.cwpark.library.controller.restcontroller.admin;

import com.cwpark.library.data.dto.guide.qna.QnaFormDto;
import com.cwpark.library.service.SettingService;
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
@RequestMapping("/api/v1/admin/setting")
public class SettingRestController {
    private final SettingService settingService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search() {
        Map<String, Object> result = new HashMap<>();

        result.put("result", settingService.findAll());

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@RequestParam("id") String id, @RequestParam("value") String value) {
        Map<String, Object> result = new HashMap<>();

        settingService.update(id, value);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
