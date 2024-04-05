package com.cwpark.library.dao;

import com.cwpark.library.data.dto.SettingDto;
import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserMyPageDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.repository.SettingRepository;
import com.cwpark.library.repository.UserRepository;
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

}
