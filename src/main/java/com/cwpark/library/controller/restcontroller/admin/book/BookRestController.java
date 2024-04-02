package com.cwpark.library.controller.restcontroller.admin.book;

import com.cwpark.library.data.dto.book.book.BookFormDto;
import com.cwpark.library.service.book.BookService;
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
@RequestMapping("/api/v1/admin/book")
public class BookRestController {
    private final BookService service;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@ModelAttribute BookFormDto dto) {
        Map<String, Object> result = new HashMap<>();

        if(dto.getPostType().equals("insert")) {
            if(service.existsById(dto.getBookIsbn())) {
                result.put("success", "N");
            } else {
                service.insert(dto);
            }
        } else {

        }
        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/searchPage")
    public ResponseEntity<Map<String, Object>> searchPage(@RequestParam("title") String title, @PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", service.searchPage(title, pageable));

        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> searchPage(@RequestParam("bookIsbn") String bookIsbn) {
        Map<String, Object> result = new HashMap<>();
        service.delete(bookIsbn);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
