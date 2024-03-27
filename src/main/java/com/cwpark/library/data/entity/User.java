package com.cwpark.library.data.entity;

import com.cwpark.library.data.dto.UserInsertDto;
import com.cwpark.library.data.dto.UserSelectDto;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "USERS")
public class User extends BaseEntity {

    @Id
    @Column(name = "USER_ID")
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

    @Column(name = "USER_FIND_PASSWORD_YN")
    private String userFindPasswordYn;

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

    public User(String id, String password2) {
        this.userId = id;
        this.userPassword = password2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return getUserLoginFailCnt() == user.getUserLoginFailCnt() && Objects.equals(getUserId(), user.getUserId()) && Objects.equals(getUserPassword(), user.getUserPassword()) && Objects.equals(getUserName(), user.getUserName()) && Objects.equals(getUserSex(), user.getUserSex()) && Objects.equals(getUserBirth(), user.getUserBirth()) && getUserOauthType() == user.getUserOauthType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUserPassword(), getUserName(), getUserSex(), getUserBirth(), getUserLoginFailCnt(), getUserOauthType());
    }

    public static User insertToEntity(UserInsertDto user) {
        return User.builder()
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .userName(user.getUserName())
                .userSex(user.getUserSex())
                .userBirth(user.getUserBirth())
                .userAuthority(UserAuthority.USER)
                .userLoginFailCnt(0)
                .userOauthType(user.getUserOauthType() == null ? UserOauthType.EMALE : user.getUserOauthType())
                .userFindPasswordYn("N")
                .build();
    }

    public static User selectToEntity(UserSelectDto user) {
        return User.builder()
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

}
