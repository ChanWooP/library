package com.cwpark.library.data.entity;

import com.cwpark.library.data.dto.user.UserInsertDto;
import com.cwpark.library.data.dto.user.UserLikeDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.Book;
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
@Table(name = "USER_LIKE")
@IdClass(UserLikeId.class)
public class UserLike extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIKE_USER")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIKE_ISBN")
    private Book book;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserLike userLike = (UserLike) object;
        return Objects.equals(getUser(), userLike.getUser()) && Objects.equals(getBook(), userLike.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getBook());
    }

    public static UserLike toEntity(UserLikeDto userLikeDto) {
        return UserLike.builder()
                .user(User.selectToEntity(userLikeDto.getUser()))
                .book(Book.selectToEntity(userLikeDto.getBook()))
                .build();
    }
}
