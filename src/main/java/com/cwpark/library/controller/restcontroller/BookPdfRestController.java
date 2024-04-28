package com.cwpark.library.controller.restcontroller;

import com.cwpark.library.service.BookPdfService;
import com.cwpark.library.service.book.BookLoanService;
import com.cwpark.library.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookPdfRestController {
    private final BookPdfService bookPdfService;

    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> getPdf(@RequestParam("userId") String userId, @RequestParam("bookIsbn") String bookIsbn) throws FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        String pdf = bookPdfService.getPdf(userId, bookIsbn);
        headers.add("content-disposition", "inline;filename=1.pdf");

        File file = new File(pdf);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
