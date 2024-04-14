package com.cwpark.library.controller.restcontroller.admin.book;

import com.cwpark.library.data.enums.BookHopeStatus;
import com.cwpark.library.service.book.BookHopeService;
import com.cwpark.library.service.book.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/hope")
public class BookHopeRestController {
    private final BookHopeService bookHopeService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getBookHope(@RequestParam("bookHopeStatus") BookHopeStatus bookHopeStatus,
                                                           @RequestParam("frDt") String frDt, @RequestParam("toDt") String toDt,
                                                           @RequestParam("search") String search, @PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();

        result.put("result", bookHopeService.searchPage(bookHopeStatus, frDt, toDt, search, pageable));

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> loanReturn(@RequestParam("id") Long id, @RequestParam("status") BookHopeStatus status) {
        Map<String, Object> result = new HashMap<>();
        bookHopeService.statusUpdate(id, status);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
