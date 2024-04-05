package com.cwpark.library.data.dto.book;

import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.BookLoan;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLoanDto {

    private Long loanId;

    @NotBlank(message = "책은 필수 입력 사항입니다")
    private BookSelectDto book;

    @NotBlank(message = "대출일자는 필수 입력 사항입니다")
    private LocalDateTime loanDate;

    @NotBlank(message = "반납일자는 입력 사항입니다")
    private LocalDateTime loanReturnDate;

    @NotBlank(message = "대출자는 필수 입력 사항입니다")
    private UserSelectDto user;

    @NotBlank(message = "반납여부는 필수 입력 사항입니다")
    private String loanReturnYn;

    public static BookLoanDto toDto(BookLoan bookLoan) {
        return BookLoanDto.builder()
                .loanId(bookLoan.getLoanId())
                .book(BookSelectDto.toDto(bookLoan.getBook()))
                .loanDate(bookLoan.getLoanDate())
                .loanReturnDate(bookLoan.getLoanReturnDate())
                .user(UserSelectDto.toDto(bookLoan.getUser()))
                .loanReturnYn(bookLoan.getLoanReturnYn())
                .build();
    }
}
