package com.cwpark.library.repository.guide.notify;

import com.cwpark.library.data.entity.book.BookHope;
import com.cwpark.library.data.entity.guide.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, Long>, NotifyRepositoryCustom {

}