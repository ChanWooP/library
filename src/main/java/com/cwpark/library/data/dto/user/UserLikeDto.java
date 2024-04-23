package com.cwpark.library.data.dto.user;

import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.UserLike;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserLikeDto {

    @NotBlank(message = "아이디는 필수 입력 사항 입니다")
    private UserSelectDto user;

    @NotBlank(message = "ISBN은 필수 입력 사항 입니다")
    private BookSelectDto book;

    @QueryProjection
    public UserLikeDto(UserSelectDto user, BookSelectDto book) {
        this.user = user;
        this.book = book;
    }

    public static UserLikeDto toDto(UserLike userLike) {
        return UserLikeDto.builder()
                .user(UserSelectDto.toDto(userLike.getUser()))
                .book(BookSelectDto.toDto(userLike.getBook()))
                .build();
    }
}
