package com.cwpark.library.data.dto;

import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMyPageDto {

    @NotBlank(message = "아이디는 필수 입력 사항 입니다")
    private String userId;

    @NotBlank(message = "이름은 필수 입력 사항 입니다")
    private String userName;

    @NotBlank(message = "생년월일은 필수 입력 사항 입니다")
    private String userBirth;

    @NotBlank(message = "성별은 필수 입력 사항 입니다")
    private String userSex;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserMyPageDto myPageDto = (UserMyPageDto) object;
        return Objects.equals(getUserId(), myPageDto.getUserId()) && Objects.equals(getUserName(), myPageDto.getUserName()) && Objects.equals(getUserBirth(), myPageDto.getUserBirth()) && Objects.equals(getUserSex(), myPageDto.getUserSex());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUserName(), getUserBirth(), getUserSex());
    }
}
