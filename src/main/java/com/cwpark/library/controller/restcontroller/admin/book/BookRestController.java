package com.cwpark.library.controller.restcontroller.admin.book;

import com.cwpark.library.data.dto.book.book.BookFormDto;
import com.cwpark.library.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/book")
public class BookRestController {
    private final BookService service;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save( @ModelAttribute BookFormDto dto) {
        Map<String, Object> result = new HashMap<>();
        service.save(dto);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
