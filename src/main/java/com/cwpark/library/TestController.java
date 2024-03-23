package com.cwpark.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test/{string}")
    public Map<String, Object> getString(@PathVariable("string") String string) {
        Map<String, Object> result = new HashMap<>();

        if(testService.getString(string).equals("success")) {
            result.put("success", "Y");
        } else {
            result.put("success", "N");
        }

        return result;
    }

    @PostMapping("/test")
    public Map<String, Object> postString(@RequestBody Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        if(testService.postString(map.get("string").toString()).equals("success")) {
            result.put("success", "Y");
        } else {
            result.put("success", "N");
        }

        return result;
    }
}
