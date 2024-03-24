package com.cwpark.library.data.dto;

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
public class UserInsertDto {

    @NotBlank(message = "아이디는 필수 입력 사항 입니다")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 사항 입니다")
    private String userPassword;

    @NotBlank(message = "이름은 필수 입력 사항 입니다")
    private String userName;

    @NotBlank(message = "성별은 필수 입력 사항 입니다")
    private String userSex;

    @NotBlank(message = "생년월일은 필수 입력 사항 입니다")
    private String userBirth;

    private UserOauthType userOauthType;

    private UserAuthority userAuthority;

    public static UserInsertDto kakaoToDto(UserKakaoDto user) {
        return UserInsertDto.builder()
                .userId(user.getEmail())
                .userName(user.getNickName())
                .userPassword(user.getPassword())
                .userOauthType(user.getUserOauthType())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserInsertDto that = (UserInsertDto) object;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getUserPassword(), that.getUserPassword()) && Objects.equals(getUserName(), that.getUserName()) && Objects.equals(getUserSex(), that.getUserSex()) && Objects.equals(getUserBirth(), that.getUserBirth()) && getUserOauthType() == that.getUserOauthType() && getUserAuthority() == that.getUserAuthority();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUserPassword(), getUserName(), getUserSex(), getUserBirth(), getUserOauthType(), getUserAuthority());
    }
}
