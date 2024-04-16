package com.cwpark.library.repository.guide.qna;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.dto.guide.qna.QnaDto;
import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.guide.Qna;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.repository.user.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestRepositoryConfig.class)
@Transactional
@Slf4j
class QnaRepositoryImplTest {
    @Autowired
    QnaRepository qnaRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("관리자 조회")
    void admin() {
        User user = new User(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0 ,UserOauthType.EMALE, "N");
        userRepository.save(user);

        Qna qna = new Qna(null, user, "20240416", "q", "a", "Y");
        qnaRepository.save(qna);

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<QnaDto> qnaList = qnaRepository.searchPage(null, "20240415", "20240616", pageRequest);

        Assertions.assertEquals(qnaList.getContent().size(), 1);
    }
}