package com.cwpark.library.dao.guide;

import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.entity.book.BookHope;
import com.cwpark.library.data.entity.guide.Notify;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.cwpark.library.repository.book.hope.BookHopeRepository;
import com.cwpark.library.repository.guide.notify.NotifyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotifyDao {
    private final NotifyRepository notifyRepository;

    public Page<NotifyDto> searchPage(String nowDate, String search, Pageable pageable) {
        return notifyRepository.searchPage(nowDate, search, pageable);
    }

    public Page<NotifyDto> searchPage(String frDt, String toDt, String search, Pageable pageable) {
        return notifyRepository.searchPage(frDt, toDt, search, pageable);
    }

    public List<NotifyDto> findTop5ByOrderByNotifyStartDtDesc() {
        return notifyRepository.findTop5ByOrderByNotifyStartDtDesc().stream()
                .map(NotifyDto::toDto).collect(Collectors.toList());
    }

    public NotifyDto findById(Long id) {
        Notify notify = notifyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다"));

        return NotifyDto.toDto(notify);
    }

    public void insert(NotifyDto notifyDto) {
        notifyRepository.save(Notify.toEntity(notifyDto));
    }

    public void update(NotifyDto notifyDto) {
        Notify notify = notifyRepository.findById(notifyDto.getNotifyId())
                .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다"));

        notify.setNotifyType(notifyDto.getNotifyType());
        notify.setNotifyTitle(notifyDto.getNotifyTitle());
        notify.setNotifyText(notifyDto.getNotifyText());

        if(notifyDto.getNotifyImg() != null) {
            notify.setNotifyImg(notifyDto.getNotifyImg());
        }

        notify.setNotifyStartDt(notifyDto.getNotifyStartDt());
        notify.setNotifyEndDt(notifyDto.getNotifyEndDt());
    }

    public void delete(Long id) {
        Notify notify = notifyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다"));

        notifyRepository.delete(notify);
    }

}
