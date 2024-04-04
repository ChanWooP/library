package com.cwpark.library.data.dto.book;

import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.BookReserve;
import com.cwpark.library.data.enums.BookReserveType;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReserveDto {

    private Long reserveId;

    @NotBlank(message = "책은 필수 입력 사항입니다")
    private BookSelectDto book;

    @NotBlank(message = "예약날짜는 필수 입력 사항입니다")
    private LocalDateTime reserveDate;

    @NotBlank(message = "예약자는 필수 입력 사항입니다")
    private UserSelectDto user;

    @NotBlank(message = "예약 상태코드는 필수 입력 사항입니다")
    private BookReserveType reserveStatus;

    public static BookReserveDto toDto(BookReserve bookReserve) {
        return BookReserveDto.builder()
                .reserveId(bookReserve.getReserveId())
                .book(BookSelectDto.toDto(bookReserve.getBook()))
                .reserveDate(bookReserve.getReserveDate())
                .user(UserSelectDto.toDto(bookReserve.getUser()))
                .reserveStatus(bookReserve.getReserveStatus())
                .build();
    }

}
