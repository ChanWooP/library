package com.cwpark.library.data.entity.book;

import com.cwpark.library.dao.book.BookHopeDao;
import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.entity.BaseEntity;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.BookHopeStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "BOOK_HOPE")
public class BookHope extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOPE_ID")
    private Long hopeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOPE_USER")
    private User user;

    @Column(name = "HOPE_ISBN")
    private String hopeIsbn;

    @Column(name = "HOPE_TITLE")
    private String hopTitle;

    @Column(name = "HOPE_AUTHOR")
    private String hopeAuthor;

    @Column(name = "HOPE_PUBLISHER")
    private String hopePublisher;

    @Column(name = "HOPE_DATE")
    private String hopeDate;

    @Column(name = "HOPE_STATUS")
    @Enumerated(EnumType.STRING)
    private BookHopeStatus hopeStatus;

    public static BookHope toEntity(BookHopeDto dto) {
        return BookHope.builder()
                .hopeId(dto.getHopeId())
                .user(User.selectToEntity(dto.getUser()))
                .hopeIsbn(dto.getHopeIsbn())
                .hopTitle(dto.getHopeTitle())
                .hopeAuthor(dto.getHopeAuthor())
                .hopePublisher(dto.getHopePublisher())
                .hopeDate(dto.getHopeDate())
                .hopeStatus(dto.getHopeStatus())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BookHope bookHope = (BookHope) object;
        return Objects.equals(getHopeId(), bookHope.getHopeId()) && Objects.equals(getUser(), bookHope.getUser()) && Objects.equals(getHopeIsbn(), bookHope.getHopeIsbn()) && Objects.equals(getHopTitle(), bookHope.getHopTitle()) && Objects.equals(getHopeAuthor(), bookHope.getHopeAuthor()) && Objects.equals(getHopePublisher(), bookHope.getHopePublisher()) && Objects.equals(getHopeDate(), bookHope.getHopeDate()) && getHopeStatus() == bookHope.getHopeStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHopeId(), getUser(), getHopeIsbn(), getHopTitle(), getHopeAuthor(), getHopePublisher(), getHopeDate(), getHopeStatus());
    }
}
