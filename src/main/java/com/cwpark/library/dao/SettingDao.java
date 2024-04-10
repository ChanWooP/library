package com.cwpark.library.dao;

import com.cwpark.library.data.dto.SettingDto;
import com.cwpark.library.repository.SettingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingDao {

    private final SettingRepository repository;

    public SettingDto findById(String setId){
        return SettingDto.toDto(repository.findById(setId).orElseThrow(() -> new EntityNotFoundException("셋팅이 존재하지 않습니다")));
    }

}
