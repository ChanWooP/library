package com.cwpark.library.data.dto.user;

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
public class UserSelectDto {

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

    @NotBlank(message = "권한은 필수 입력 사항 입니다")
    private UserAuthority userAuthority;

    @NotBlank(message = "로그인 실패횟수는 필수 입력 사항 입니다")
    private int userLoginFailCnt;

    @NotBlank(message = "가입 경로는 필수 입력 사항 입니다")
    private UserOauthType userOauthType;

    public static UserSelectDto toDto(User user) {
        return UserSelectDto.builder()
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .userName(user.getUserName())
                .userSex(user.getUserSex())
                .userBirth(user.getUserBirth())
                .userAuthority(user.getUserAuthority())
                .userLoginFailCnt(user.getUserLoginFailCnt())
                .userOauthType(user.getUserOauthType())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserSelectDto that = (UserSelectDto) object;
        return getUserLoginFailCnt() == that.getUserLoginFailCnt() && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getUserPassword(), that.getUserPassword()) && Objects.equals(getUserName(), that.getUserName()) && Objects.equals(getUserSex(), that.getUserSex()) && Objects.equals(getUserBirth(), that.getUserBirth()) && getUserAuthority() == that.getUserAuthority() && getUserOauthType() == that.getUserOauthType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUserPassword(), getUserName(), getUserSex(), getUserBirth(), getUserAuthority(), getUserLoginFailCnt(), getUserOauthType());
    }
}
