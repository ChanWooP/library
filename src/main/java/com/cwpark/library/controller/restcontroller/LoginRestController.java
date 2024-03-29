package com.cwpark.library.controller.restcontroller;

import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sign-in")
public class LoginRestController {

    private final UserService userService;

    @GetMapping("/join/{userId}")
    public ResponseEntity<Map<String, Object>> existsByUserId(@PathVariable(name = "userId") String userId) {
        Map<String, Object> result = new HashMap<>();

        if(userService.existsById(userId)) {
            result.put("success", "N");
            result.put("message", "아이디가 중복되었습니다.");
        } else {
            result.put("success", "Y");
            result.put("message", "사용 가능한 아이디 입니다.");
        }

        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/find/id")
    public ResponseEntity<Map<String, Object>> findById(@RequestParam("userName") String userName, @RequestParam("userBirth") String userBirth) {
        Map<String, Object> result = new HashMap<>();

        List<String> list = userService.findById(userName, userBirth)
                .stream().map(UserSelectDto::getUserId).toList();
        result.put("userList", list);

        return ResponseEntity.ok()
                .body(result);
    }

}
