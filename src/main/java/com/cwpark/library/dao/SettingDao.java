package com.cwpark.library.dao;

import com.cwpark.library.data.dto.SettingDto;
import com.cwpark.library.data.entity.Setting;
import com.cwpark.library.repository.SettingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettingDao {

    private final SettingRepository repository;

    public SettingDto findById(String setId){
        return SettingDto.toDto(repository.findById(setId).orElseThrow(() -> new EntityNotFoundException("셋팅이 존재하지 않습니다")));
    }

    public List<SettingDto> findAll() {
        List<Setting> all = repository.findAll();
        return all.stream().map(SettingDto::toDto).collect(Collectors.toList());
    }

    public void update(String setId, String value) {
        Setting set = repository.findById(setId).orElseThrow(() -> new EntityNotFoundException("셋팅이 존재하지 않습니다"));

        set.setSetValue(value);
    }
}
