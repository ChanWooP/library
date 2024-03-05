package com.cwpark.library.data.entity;

import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", unique = true)
    private String userId;

    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_SEX")
    private String userSex;

    @Column(name = "USER_BIRTH")
    private String userBirth;

    @Column(name = "USER_AUTHORITY")
    @Enumerated(EnumType.STRING)
    private UserAuthority userAuthority;

    @Column(name = "USER_LOGIN_FAIL_CNT")
    private int userLoginFailCnt;

    @Column(name = "USER_OAUTH_TYPE")
    @Enumerated(EnumType.STRING)
    private UserOauthType userOauthType;

    public User(String userId, String userPassword, String userName, String userSex,
                String userBirth, int userLoginFailCnt, UserOauthType userOauthType) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userSex = userSex;
        this.userBirth = userBirth;
        this.userLoginFailCnt = userLoginFailCnt;
        this.userOauthType = userOauthType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return getUserLoginFailCnt() == user.getUserLoginFailCnt() && Objects.equals(getId(), user.getId()) && Objects.equals(getUserId(), user.getUserId()) && Objects.equals(getUserPassword(), user.getUserPassword()) && Objects.equals(getUserName(), user.getUserName()) && Objects.equals(getUserSex(), user.getUserSex()) && Objects.equals(getUserBirth(), user.getUserBirth()) && getUserOauthType() == user.getUserOauthType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getUserPassword(), getUserName(), getUserSex(), getUserBirth(), getUserLoginFailCnt(), getUserOauthType());
    }
}
