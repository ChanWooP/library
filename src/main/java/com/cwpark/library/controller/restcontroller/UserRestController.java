package com.cwpark.library.controller.restcontroller;

import com.cwpark.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserRestController {

    private final UserService userService;

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("userId") String userId) {
        Map<String, Object> result = new HashMap<>();

        userService.deleteUser(userId);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
