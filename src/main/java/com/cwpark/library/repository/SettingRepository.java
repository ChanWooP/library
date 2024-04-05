package com.cwpark.library.repository;

import com.cwpark.library.data.entity.Setting;
import com.cwpark.library.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingRepository extends JpaRepository<Setting, String> {
}
