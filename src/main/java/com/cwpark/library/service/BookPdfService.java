package com.cwpark.library.service;

import com.cwpark.library.config.aop.SameUserCheck;
import com.cwpark.library.dao.UserDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.dao.book.BookLoanDao;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookPdfService {
    private final BookDao bookDao;
    private final BookLoanDao bookLoanDao;
    private final UserDao userDao;

    @Value("${file.dir}")
    private String fileDir;

    private final static String FILE_PATH = "book/";
    private final static String IMG_FILE_PATH = "/img";

    @SameUserCheck
    public String getPdf(String userId, String bookIsbn) {
        UserSelectDto user = userDao.findById(userId);
        BookSelectDto book = bookDao.findById(bookIsbn);

        if(bookLoanDao.findByUserAndBookAndLoanReturnYn(user, book) != null) {
            return fileDir + FILE_PATH + book.getBookIsbn() + IMG_FILE_PATH + "/1.pdf";
        } else {
            return null;
        }
    }
}
