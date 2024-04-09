package com.cwpark.library.data.entity.book;

import com.cwpark.library.data.dto.book.BookReserveDto;
import com.cwpark.library.data.entity.BaseEntity;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.BookReserveType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "BOOK_RESERVE")
public class BookReserve extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVE_ID")
    private Long reserveId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESERVE_ISBN")
    private Book book;

    @Column(name = "RESERVE_DATE")
    private LocalDateTime reserveDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESERVE_USER")
    private User user;

    @Column(name = "RESERVE_STATUS")
    @Enumerated(EnumType.STRING)
    private BookReserveType reserveStatus;

    public static BookReserve toEntity(BookReserveDto dto) {
        return BookReserve.builder()
                .reserveId(dto.getReserveId())
                .book(Book.selectToEntity(dto.getBook()))
                .reserveDate(dto.getReserveDate())
                .user(User.selectToEntity(dto.getUser()))
                .reserveStatus(dto.getReserveStatus())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BookReserve that = (BookReserve) object;
        return Objects.equals(getReserveId(), that.getReserveId()) && Objects.equals(getBook(), that.getBook()) && Objects.equals(getReserveDate(), that.getReserveDate()) && Objects.equals(getUser(), that.getUser()) && getReserveStatus() == that.getReserveStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReserveId(), getBook(), getReserveDate(), getUser(), getReserveStatus());
    }

}
