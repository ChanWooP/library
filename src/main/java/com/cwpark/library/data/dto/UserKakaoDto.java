package com.cwpark.library.data.dto;

import com.cwpark.library.data.enums.UserOauthType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserKakaoDto {
    @NotBlank(message = "아이디는 필수 입력 사항 입니다")
    private String email;

    @NotBlank(message = "이름은 필수 입력 사항 입니다")
    private String nickName;

    @NotBlank(message = "비밀번호는 필수 입력 사항 입니다")
    private String password;

    @NotBlank(message = "가입경로는 필수 입력 사항 입니다")
    private UserOauthType userOauthType;

    public static UserKakaoDto toDto(String email, String nickName, String password, UserOauthType userOauthType) {
        return UserKakaoDto.builder()
                .email(email)
                .nickName(nickName)
                .password(password)
                .userOauthType(userOauthType)
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserKakaoDto that = (UserKakaoDto) object;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getNickName(), that.getNickName()) && Objects.equals(getPassword(), that.getPassword()) && getUserOauthType() == that.getUserOauthType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getNickName(), getPassword(), getUserOauthType());
    }
}
