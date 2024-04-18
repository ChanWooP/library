package com.cwpark.library.repository.guide.qna;

import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.dto.guide.qna.QnaDto;
import com.cwpark.library.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaRepositoryCustom {
    Page<QnaDto> searchPage(User user, String frDt, String toDt, Pageable pageable);
    Page<QnaDto> searchPage(Pageable pageable);
}
