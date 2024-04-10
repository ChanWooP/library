package com.cwpark.library.controller.restcontroller.admin.user;

import com.cwpark.library.data.dto.book.book.BookFormDto;
import com.cwpark.library.data.dto.user.UserAdminFormDto;
import com.cwpark.library.data.enums.BookReserveType;
import com.cwpark.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/user")
public class UserInfoRestController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info(@RequestParam("search") String search, @PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();

        result.put("result", userService.userList(search, pageable));

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/info")
    public ResponseEntity<Map<String, Object>> infoSave(@ModelAttribute UserAdminFormDto userAdminFormDto) {
        Map<String, Object> result = new HashMap<>();

        userService.infoSave(userAdminFormDto);

        result.put("success", "Y");

        return ResponseEntity.ok()
                .body(result);
    }
}
