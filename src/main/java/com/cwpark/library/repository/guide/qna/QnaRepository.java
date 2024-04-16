package com.cwpark.library.repository.guide.qna;

import com.cwpark.library.data.entity.guide.Notify;
import com.cwpark.library.data.entity.guide.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long>, QnaRepositoryCustom {

}