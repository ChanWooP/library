package com.cwpark.library.service.book;

import com.cwpark.library.config.file.FileStore;
import com.cwpark.library.dao.book.BookCategoryDao;
import com.cwpark.library.dao.book.BookDao;
import com.cwpark.library.data.dto.book.book.BookFormDto;
import com.cwpark.library.data.dto.book.book.BookInsUpdDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDao;
    private final BookCategoryDao bookCategoryDao;
    private final FileStore fileStore;

    private final static String FILE_PATH = "book/";
    private final static String IMG_FILE_PATH = "/img";

    public Boolean existsById(String bookIsbn) {
        return bookDao.existsById(bookIsbn);
    }

    public void insert(BookFormDto dto) {
        BookInsUpdDto book = BookInsUpdDto.toDto(dto);
        book.setBookCategory(bookCategoryDao.findById(dto.getBookCategory()));

        String fileName = fileStore.storeFile(FILE_PATH + dto.getBookIsbn(), dto.getMultipartFile(), dto.getBookIsbn());
        fileStore.storeFiles(FILE_PATH + dto.getBookIsbn() + IMG_FILE_PATH, dto.getMultipartFiles());

        book.setBookImage(fileName);

        bookDao.save(book);
    }

    public void update(BookFormDto dto) {
        BookSelectDto findBook = bookDao.findById(dto.getBookIsbn());
        findBook.setBookCategory(bookCategoryDao.findById(dto.getBookCategory()));
        findBook.setBookTitle(dto.getBookTitle());
        findBook.setBookAuthor(dto.getBookAuthor());
        findBook.setBookPublisher(dto.getBookPublisher());
        findBook.setBookDistributor(dto.getBookDistributor());
        findBook.setBookPublicationYear(dto.getBookPublicationYear());
        findBook.setBookIndex(dto.getBookIndex());
        findBook.setBookInt(dto.getBookInt());
        findBook.setBookAuthorInt(dto.getBookAuthorInt());
        findBook.setBookMaxLoanCnt(dto.getBookMaxLoanCnt());
        findBook.setBookMaxReserveCnt(dto.getBookMaxReserveCnt());
        findBook.setBookTotalPageCnt(dto.getBookTotalPageCnt());

        if(!dto.getMultipartFile().isEmpty()) {
            fileStore.deleteFile(findBook.getBookImage());
            String fileName = fileStore.storeFile(FILE_PATH + dto.getBookIsbn(), dto.getMultipartFile(), dto.getBookIsbn());
            findBook.setBookImage(fileName);
        }

        if(!dto.getMultipartFiles().get(0).isEmpty()) {
            fileStore.deleteFiles(FILE_PATH + dto.getBookIsbn() + IMG_FILE_PATH, false);
            fileStore.storeFiles(FILE_PATH + dto.getBookIsbn() + IMG_FILE_PATH, dto.getMultipartFiles());
        }

        bookDao.save(BookInsUpdDto.selectToDto(findBook));
    }

    public Page<BookSelectDto> searchPage(String title, Pageable pageable) {
        return bookDao.searchPage(title, pageable);
    }

    public void delete(String bookIsbn) {
        BookSelectDto findBook = bookDao.findById(bookIsbn);
        bookDao.delete(findBook);

        fileStore.deleteFiles(FILE_PATH + bookIsbn, false);
    }
}
