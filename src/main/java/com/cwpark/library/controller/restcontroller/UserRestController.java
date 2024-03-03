package com.cwpark.library.controller.restcontroller;

import com.cwpark.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/join/{userId}")
    public ResponseEntity<Map<String, Object>> existsByUserId(@PathVariable(name = "userId") String userId) {
        Map<String, Object> result = new HashMap<>();

        if(userService.existsByUserId(userId)) {
            result.put("success", "N");
            result.put("message", "아이디가 중복되었습니다.");
        } else {
            result.put("success", "Y");
            result.put("message", "사용 가능한 아이디 입니다.");
        }

        return ResponseEntity.ok()
                .body(result);
    }

}
