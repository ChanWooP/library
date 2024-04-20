package com.cwpark.library.data.dto.book;

import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.BookHope;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class BookHopeDto {

    private Long hopeId;

    @NotBlank(message = "사용자는 필수 입력 사항입니다")
    private UserSelectDto user;

    @NotBlank(message = "ISBN은 필수 입력 사항입니다")
    private String hopeIsbn;

    @NotBlank(message = "제목은 필수 입력 사항입니다")
    private String hopeTitle;

    @NotBlank(message = "저자는 필수 입력 사항입니다")
    private String hopeAuthor;

    @NotBlank(message = "출판사는 필수 입력 사항입니다")
    private String hopePublisher;

    @NotBlank(message = "요청일자는 필수 입력 사항입니다")
    private String hopeDate;

    @NotBlank(message = "상태값은 필수 입력 사항입니다")
    private BookHopeStatus hopeStatus;

    @QueryProjection
    public BookHopeDto(Long hopeId, UserSelectDto user, String hopeIsbn, String hopeTitle, String hopeAuthor, String hopePublisher, String hopeDate, BookHopeStatus hopeStatus) {
        this.hopeId = hopeId;
        this.user = user;
        this.hopeIsbn = hopeIsbn;
        this.hopeTitle = hopeTitle;
        this.hopeAuthor = hopeAuthor;
        this.hopePublisher = hopePublisher;
        this.hopeDate = hopeDate;
        this.hopeStatus = hopeStatus;
    }

    public static BookHopeDto toDto(BookHope bookHope) {
        return BookHopeDto.builder()
                .hopeId(bookHope.getHopeId())
                .user(UserSelectDto.toDto(bookHope.getUser()))
                .hopeIsbn(bookHope.getHopeIsbn())
                .hopeTitle(bookHope.getHopTitle())
                .hopeAuthor(bookHope.getHopeAuthor())
                .hopePublisher(bookHope.getHopePublisher())
                .hopeDate(bookHope.getHopeDate())
                .hopeStatus(bookHope.getHopeStatus())
                .build();
    }

    public static BookHopeDto formToDto(BookHopeFormDto bookHope) {
        return BookHopeDto.builder()
                .user(bookHope.getUser())
                .hopeIsbn(bookHope.getHopeIsbn())
                .hopeTitle(bookHope.getHopeTitle())
                .hopeAuthor(bookHope.getHopeAuthor())
                .hopePublisher(bookHope.getHopePublisher())
                .hopeDate(bookHope.getHopeDate())
                .hopeStatus(bookHope.getHopeStatus())
                .build();
    }
}
