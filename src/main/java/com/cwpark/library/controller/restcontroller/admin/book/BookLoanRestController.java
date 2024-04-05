package com.cwpark.library.controller.restcontroller.admin.book;

import com.cwpark.library.service.book.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/loan")
public class BookLoanRestController {
    private final BookLoanService bookLoanService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getLoanReserve(@RequestParam("bookIsbn") String bookIsbn) {
        Map<String, Object> result = new HashMap<>();

        result.put("content", bookLoanService.findByBookAndLoanReturnYn(bookIsbn, "N"));

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Object>> loanReturn(@RequestParam("loanId") Long loanId) {
        Map<String, Object> result = new HashMap<>();
        bookLoanService.loanReturn(loanId);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
