package com.cwpark.library.data.dto.book.book;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.entity.book.BookCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookFormDto {

    private String postType;

    @NotBlank(message = "ISBN은 필수 입력 사항 입니다")
    private String bookIsbn;

    @NotNull(message = "카테고리는 필수 입력 사항 입니다")
    private Long bookCategory;

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

    @NotNull(message = "최대 대여횟수는 필수 입력 사항 입니다")
    private int bookMaxLoanCnt;

    @NotNull(message = "최대 예약횟수는 필수 입력 사항 입니다")
    private int bookMaxReserveCnt;

    @NotNull(message = "총 페이지 수는 필수 입력 사항 입니다")
    private int bookTotalPageCnt;

    private String bookImage;

    private MultipartFile multipartFile;

    private List<MultipartFile> multipartFiles;
}
