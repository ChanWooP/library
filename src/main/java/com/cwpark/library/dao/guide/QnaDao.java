package com.cwpark.library.dao.guide;

import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.dto.guide.qna.QnaDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.guide.Notify;
import com.cwpark.library.data.entity.guide.Qna;
import com.cwpark.library.repository.guide.notify.NotifyRepository;
import com.cwpark.library.repository.guide.qna.QnaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaDao {
    private final QnaRepository qnaRepository;

    public Page<QnaDto> searchPage(User user, String frDt, String toDt, Pageable pageable) {
        return qnaRepository.searchPage(user, frDt, toDt, pageable);
    }

    public void insert(QnaDto qnaDto) {
        qnaRepository.save(Qna.toEntity(qnaDto));
    }

    public void update(QnaDto qnaDto) {
        Qna qna = qnaRepository.findById(qnaDto.getQnaId())
                .orElseThrow(() -> new EntityNotFoundException("질문이 존재하지 않습니다"));

        qna.setQnaAnswerYn("Y");
        qna.setQnaAnswer(qnaDto.getQnaAnswer());
    }

    public void delete(Long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("질문이 존재하지 않습니다"));

        qnaRepository.delete(qna);
    }

}
