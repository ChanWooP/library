package com.cwpark.library.controller.restcontroller;

import com.cwpark.library.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailRestController {

    private final EmailService emailService;

    @GetMapping("/check/{userEmail}")
    public ResponseEntity<Map<String, Object>> emailConfirm(@PathVariable(name = "userEmail") String userEmail) throws Exception {

        Map<String, Object> result = new HashMap<>();
        result.put("check", emailService.sendSimpleMessage(userEmail));

        return ResponseEntity.ok()
                .body(result);
    }

}