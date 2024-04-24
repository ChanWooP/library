package com.cwpark.library.controller.restcontroller.admin.book;

import com.cwpark.library.data.enums.BookReserveType;
import com.cwpark.library.service.book.BookLoanService;
import com.cwpark.library.service.book.BookReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/reserve")
public class BookReserveRestController {
    private final BookReserveService bookReserveService;

    @GetMapping("/search/book")
    public ResponseEntity<Map<String, Object>> getReserveBook(@RequestParam("bookIsbn") String bookIsbn) {
        Map<String, Object> result = new HashMap<>();

        result.put("content", bookReserveService.findByReserves(bookIsbn, BookReserveType.RESERVE));

        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/search/user")
    public ResponseEntity<Map<String, Object>> getReserveUser(@RequestParam("userId") String userId) {
        Map<String, Object> result = new HashMap<>();

        result.put("content", bookReserveService.findByUserAndReserveStatus(userId, BookReserveType.RESERVE));

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Map<String, Object>> cancel(@RequestParam("reserveId") Long reserveId) {
        Map<String, Object> result = new HashMap<>();
        bookReserveService.cancel(null, reserveId);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
