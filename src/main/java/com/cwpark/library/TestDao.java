package com.cwpark.library;

import org.springframework.stereotype.Service;

@Service
public class TestDao {

    public String getString(String string) {
        if(string.equals("string")) {
            return "success";
        } else {
            return "fail";
        }
    }

    public String postString(String string) {
        if(string.equals("string")) {
            return "success";
        } else {
            return "fail";
        }
    }
}
