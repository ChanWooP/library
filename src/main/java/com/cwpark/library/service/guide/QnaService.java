package com.cwpark.library.service.guide;

import com.cwpark.library.config.file.FileStore;
import com.cwpark.library.dao.guide.NotifyDao;
import com.cwpark.library.dao.guide.QnaDao;
import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.dto.guide.notify.NotifyFormDto;
import com.cwpark.library.data.dto.guide.qna.QnaDto;
import com.cwpark.library.data.dto.guide.qna.QnaFormDto;
import com.cwpark.library.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaService {
    private final QnaDao qnaDao;

    public Page<QnaDto> searchPage(User user, String frDt, String toDt, Pageable pageable) {
        return qnaDao.searchPage(user, frDt, toDt, pageable);
    }

    public void insert(QnaFormDto dto) {
        qnaDao.insert(QnaDto.formToDto(dto));
    }

    public void update(QnaFormDto dto) {
        qnaDao.update(QnaDto.formToDto(dto));
    }

    public void delete(Long id) {
        qnaDao.delete(id);
    }

}
