package com.cwpark.library.repository.guide.notify;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.entity.guide.Notify;
import com.cwpark.library.data.enums.NotifyType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestRepositoryConfig.class)
@Transactional
@Slf4j
class NotifyRepositoryTest {
    @Autowired
    NotifyRepository notifyRepository;

    @Test
    @DisplayName("공지사항 조회")
    void search() {
        Notify notify = new Notify(null, NotifyType.GENERAL,"title", "text", "img", "20240414", "20240416");
        notifyRepository.save(notify);

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<NotifyDto> notifies = notifyRepository.searchPage("20240415", "", pageRequest);

        Assertions.assertEquals(notifies.getContent().size(), 1);
    }

    @Test
    @DisplayName("공지사항 조회2")
    void search2() {
        Notify notify = new Notify(null, NotifyType.GENERAL,"title", "text", "img", "20240411", "20240415");
        notifyRepository.save(notify);

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<NotifyDto> notifies = notifyRepository.searchPage("", "20240412", "20240414", pageRequest);

        Assertions.assertEquals(notifies.getContent().size(), 1);
    }
}