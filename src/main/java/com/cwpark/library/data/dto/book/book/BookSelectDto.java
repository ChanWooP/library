package com.cwpark.library.data.dto.book.book;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookCategory;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class BookSelectDto {

    @NotBlank(message = "ISBN은 필수 입력 사항 입니다")
    private String bookIsbn;

    @NotBlank(message = "카테고리는 필수 입력 사항 입니다")
    private BookCategoryDto bookCategory;

    @NotBlank(message = "제목은 필수 입력 사항 입니다")
    private String bookTitle;

    @NotBlank(message = "작가는 필수 입력 사항 입니다")
    private String bookAuthor;

    @NotBlank(message = "출판사는 필수 입력 사항 입니다")
    private String bookPublisher;

    @NotBlank(message = "유툥사는 필수 입력 사항 입니다")
    private String bookDistributor;

    @NotBlank(message = "출간년도는 필수 입력 사항 입니다")
    private String bookPublicationYear;

    @NotBlank(message = "목차는 필수 입력 사항 입니다")
    private String bookIndex;

    @NotBlank(message = "책 소개는 필수 입력 사항 입니다")
    private String bookInt;

    @NotBlank(message = "작가 소개는 필수 입력 사항 입니다")
    private String bookAuthorInt;

    @NotBlank(message = "책 이미지는 필수 입력 사항 입니다")
    private String bookImage;

    @NotBlank(message = "최대 대여횟수는 필수 입력 사항 입니다")
    private int bookMaxLoanCnt;

    @NotBlank(message = "최대 예약횟수는 필수 입력 사항 입니다")
    private int bookMaxReserveCnt;

    @NotBlank(message = "좋아요 수는 필수 입력 사항 입니다")
    private int bookLike;

    @NotBlank(message = "현재 대여횟수는 필수 입력 사항 입니다")
    private int bookLoanCnt;

    @NotBlank(message = "현재 예약횟수는 필수 입력 사항 입니다")
    private int bookReserveCnt;

    @NotBlank(message = "총 페이지 수는 필수 입력 사항 입니다")
    private int bookTotalPageCnt;

    @QueryProjection
    public BookSelectDto(String bookIsbn, BookCategoryDto bookCategory, String bookTitle, String bookAuthor, String bookPublisher, String bookDistributor, String bookPublicationYear, String bookIndex, String bookInt, String bookAuthorInt, String bookImage, int bookMaxLoanCnt, int bookMaxReserveCnt, int bookLike, int bookLoanCnt, int bookReserveCnt, int bookTotalPageCnt) {
        this.bookIsbn = bookIsbn;
        this.bookCategory = bookCategory;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookDistributor = bookDistributor;
        this.bookPublicationYear = bookPublicationYear;
        this.bookIndex = bookIndex;
        this.bookInt = bookInt;
        this.bookAuthorInt = bookAuthorInt;
        this.bookImage = bookImage;
        this.bookMaxLoanCnt = bookMaxLoanCnt;
        this.bookMaxReserveCnt = bookMaxReserveCnt;
        this.bookLike = bookLike;
        this.bookLoanCnt = bookLoanCnt;
        this.bookReserveCnt = bookReserveCnt;
        this.bookTotalPageCnt = bookTotalPageCnt;
    }

    public static BookSelectDto toDto(Book entity) {
        return BookSelectDto.builder()
                .bookIsbn(entity.getBookIsbn())
                .bookCategory(BookCategoryDto.toDto(entity.getBookCategory()))
                .bookTitle(entity.getBookTitle())
                .bookAuthor(entity.getBookAuthor())
                .bookPublisher(entity.getBookPublisher())
                .bookDistributor(entity.getBookDistributor())
                .bookPublicationYear(entity.getBookPublicationYear())
                .bookIndex(entity.getBookIndex())
                .bookInt(entity.getBookInt())
                .bookAuthorInt(entity.getBookAuthor())
                .bookImage(entity.getBookImage())
                .bookMaxLoanCnt(entity.getBookMaxLoanCnt())
                .bookMaxReserveCnt(entity.getBookMaxReserveCnt())
                .bookLike(entity.getBookLike())
                .bookLoanCnt(entity.getBookLoanCnt())
                .bookReserveCnt(entity.getBookReserveCnt())
                .bookTotalPageCnt(entity.getBookTotalPageCnt())
                .build();
    }
}
