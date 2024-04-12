package com.cwpark.library.data.entity.book;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.data.dto.book.book.BookInsUpdDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.entity.BaseEntity;
import com.cwpark.library.data.entity.User;
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
@Table(name = "BOOK_LOAN")
public class BookLoan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_ID")
    private Long loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOAN_ISBN")
    private Book book;

    @Column(name = "LOAN_DATE")
    private LocalDateTime loanDate;

    @Column(name = "LOAN_RETURN_DATE")
    private LocalDateTime loanReturnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOAN_USER")
    private User user;

    @Column(name = "LOAN_RETURN_YN")
    private String loanReturnYn;

    public static BookLoan toEntity(BookLoanDto dto) {
        return BookLoan.builder()
                .loanId(dto.getLoanId())
                .book(Book.selectToEntity(dto.getBook()))
                .loanDate(dto.getLoanDate())
                .loanReturnDate(dto.getLoanReturnDate())
                .user(User.selectToEntity(dto.getUser()))
                .loanReturnYn(dto.getLoanReturnYn())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BookLoan bookLoan = (BookLoan) object;
        return Objects.equals(getLoanId(), bookLoan.getLoanId()) && Objects.equals(getBook(), bookLoan.getBook()) && Objects.equals(getLoanDate(), bookLoan.getLoanDate()) && Objects.equals(getLoanReturnDate(), bookLoan.getLoanReturnDate()) && Objects.equals(getUser(), bookLoan.getUser()) && Objects.equals(getLoanReturnYn(), bookLoan.getLoanReturnYn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanId(), getBook(), getLoanDate(), getLoanReturnDate(), getUser(), getLoanReturnYn());
    }
}
