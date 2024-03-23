package com.cwpark.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestDao testDao;

    public String getString(String string) {
        return testDao.getString(string);
    }

    public String postString(String string) {
        return testDao.postString(string);
    }
}
