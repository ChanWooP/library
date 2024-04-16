package com.cwpark.library.service;

import com.cwpark.library.dao.SettingDao;
import com.cwpark.library.dao.guide.QnaDao;
import com.cwpark.library.data.dto.SettingDto;
import com.cwpark.library.data.dto.guide.qna.QnaDto;
import com.cwpark.library.data.dto.guide.qna.QnaFormDto;
import com.cwpark.library.data.entity.Setting;
import com.cwpark.library.data.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingService {
    private final SettingDao settingDao;

    public List<SettingDto> findAll() {
        return settingDao.findAll();
    }

    public void update(String setId, String value) {
        settingDao.update(setId, value);
    }
}
