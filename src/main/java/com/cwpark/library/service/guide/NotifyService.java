package com.cwpark.library.service.guide;

import com.cwpark.library.config.file.FileStore;
import com.cwpark.library.dao.guide.NotifyDao;
import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.dto.guide.notify.NotifyFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotifyService {
    private final NotifyDao notifyDao;
    private final FileStore fileStore;

    private final static String FILE_PATH = "notify/";

    public Page<NotifyDto> searchPage(String nowDate, String search, Pageable pageable) {
        return notifyDao.searchPage(nowDate, search, pageable);
    }

    public Page<NotifyDto> searchPage(String frDt, String toDt, String search, Pageable pageable) {
        return notifyDao.searchPage(frDt, toDt, search, pageable);
    }

    public List<NotifyDto> findTop5ByOrderByNotifyStartDtDesc() {
        return notifyDao.findTop5ByOrderByNotifyStartDtDesc();
    }

    public NotifyDto findById(Long id) {
        return notifyDao.findById(id);
    }

    public void insert(NotifyFormDto dto) {
        String fileName = fileStore.storeFile2(FILE_PATH, dto.getMultipartFile());
        dto.setNotifyImg(fileName);

        notifyDao.insert(NotifyDto.formToDto(dto));
    }

    public void update(NotifyFormDto dto) {
        if(!dto.getMultipartFile().isEmpty()) {
            String fileName = fileStore.storeFile2(FILE_PATH, dto.getMultipartFile());
            dto.setNotifyImg(fileName);
        }

        notifyDao.update(NotifyDto.formToDto(dto));
    }

    public void delete(Long id) {
        notifyDao.delete(id);
    }

}
