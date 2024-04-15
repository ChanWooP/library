package com.cwpark.library.controller.restcontroller.admin.guide;

import com.cwpark.library.data.dto.guide.notify.NotifyFormDto;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.cwpark.library.service.book.BookHopeService;
import com.cwpark.library.service.guide.NotifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/notify")
public class NotifyRestController {
    private final NotifyService notifyService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("frDt") String frDt, @RequestParam("toDt") String toDt, @RequestParam("search") String search, @PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();

        result.put("result", notifyService.searchPage(frDt, toDt, search, pageable));

        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();

        result.put("result", notifyService.findById(id));

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@Valid @ModelAttribute NotifyFormDto notifyFormDto) {
        Map<String, Object> result = new HashMap<>();

        if(notifyFormDto.getType().equals("insert")) {
            notifyService.insert(notifyFormDto);
        } else {
            notifyService.update(notifyFormDto);
        }

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
