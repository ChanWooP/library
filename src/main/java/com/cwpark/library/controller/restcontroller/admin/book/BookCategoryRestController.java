package com.cwpark.library.controller.restcontroller.admin.book;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategoryFormDto;
import com.cwpark.library.service.book.BookCategoryService;
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
@RequestMapping("/api/v1/admin/category")
public class BookCategoryRestController {
    private final BookCategoryService service;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@Valid @ModelAttribute BookCategoryFormDto dto) {
        Map<String, Object> result = new HashMap<>();

        if(dto.getPostType().equals("insert")) {
            service.insert(dto);
        } else  {
            service.update(dto);
        }

        result.put("success", "Y");


        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/searchPage")
    public ResponseEntity<Map<String, Object>> searchPage(@PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", service.searchPage(pageable));

        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestParam("bookCategoryId") Long bookCategoryId) {
        Map<String, Object> result = new HashMap<>();
        service.delete(bookCategoryId);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
