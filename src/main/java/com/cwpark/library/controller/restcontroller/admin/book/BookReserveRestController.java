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

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getReserve(@RequestParam("bookIsbn") String bookIsbn) {
        Map<String, Object> result = new HashMap<>();

        result.put("content", bookReserveService.findByReserves(bookIsbn, BookReserveType.RESERVE));

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Map<String, Object>> cancel(@RequestParam("reserveId") Long reserveId) {
        Map<String, Object> result = new HashMap<>();
        bookReserveService.cancel(reserveId);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
