package com.cwpark.library.data.dto.user;

import com.cwpark.library.data.enums.UserAuthority;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAdminFormDto {
    @NotBlank(message = "아이디는 필수 입력 사항 입니다")
    private String userId;

    @NotBlank(message = "이름은 필수 입력 사항 입니다")
    private String userName;

    @NotBlank(message = "성별은 필수 입력 사항 입니다")
    private String userSex;

    @NotBlank(message = "생년월일은 필수 입력 사항 입니다")
    private String userBirth;

    @NotBlank(message = "권한은 필수 입력 사항 입니다")
    private UserAuthority userAuthority;
}
