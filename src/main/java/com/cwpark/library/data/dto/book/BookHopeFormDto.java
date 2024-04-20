package com.cwpark.library.data.dto.book;

import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.BookHope;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookHopeFormDto {

    private UserSelectDto user;

    @NotBlank(message = "사용자는 필수 입력 사항입니다")
    private String userId;

    @NotBlank(message = "ISBN은 필수 입력 사항입니다")
    private String hopeIsbn;

    @NotBlank(message = "제목은 필수 입력 사항입니다")
    private String hopeTitle;

    @NotBlank(message = "저자는 필수 입력 사항입니다")
    private String hopeAuthor;

    @NotBlank(message = "출판사는 필수 입력 사항입니다")
    private String hopePublisher;

    private String hopeDate;

    private BookHopeStatus hopeStatus;

}
