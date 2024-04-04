package com.cwpark.library.controller.restcontroller.admin.book;

import com.cwpark.library.data.dto.book.book.BookFormDto;
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
@RequestMapping("/api/v1/admin/loanreseve")
public class BookLoanReserveRestController {
    private final BookLoanService bookLoanService;
    private final BookReserveService bookReserveService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getLoanReserve(@RequestParam("bookIsbn") String bookIsbn) {
        Map<String, Object> result = new HashMap<>();

        result.put("loan", bookLoanService.findByBookAndLoanReturnYn(bookIsbn, "Y"));
        result.put("reserve", bookReserveService.findByBookAndLoanReturnYn(bookIsbn, BookReserveType.RESERVE));

        return ResponseEntity.ok()
                .body(result);
    }
}
