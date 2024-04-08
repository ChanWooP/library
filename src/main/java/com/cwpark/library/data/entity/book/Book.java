package com.cwpark.library.data.entity.book;

import com.cwpark.library.data.dto.book.book.BookInsUpdDto;
import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.entity.BaseEntity;
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
@Table(name = "BOOK")
public class Book extends BaseEntity {

    @Id
    @Column(name = "BOOK_ISBN")
    private String bookIsbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_CATEGORY_ID")
    private BookCategory bookCategory;

    @Column(name = "BOOK_TITLE")
    private String bookTitle;

    @Column(name = "BOOK_AUTHOR")
    private String bookAuthor;

    @Column(name = "BOOK_PUBLISHER")
    private String bookPublisher;

    @Column(name = "BOOK_DISTRIBUTOR")
    private String bookDistributor;

    @Column(name = "BOOK_PUBLICATION_YEAR")
    private String bookPublicationYear;

    @Column(name = "BOOK_INDEX")
    private String bookIndex;

    @Column(name = "BOOK_INT")
    private String bookInt;

    @Column(name = "BOOK_AUTHOR_INT")
    private String bookAuthorInt;

    @Column(name = "BOOK_IMAGE")
    private String bookImage;

    @Column(name = "BOOK_MAX_LOAN_CNT")
    private int bookMaxLoanCnt;

    @Column(name = "BOOK_MAX_RESERVE_CNT")
    private int bookMaxReserveCnt;

    @Column(name = "BOOK_LIKE")
    private int bookLike;

    @Column(name = "BOOK_LOAN_CNT")
    private int bookLoanCnt;

    @Column(name = "BOOK_RESERVE_CNT")
    private int bookReserveCnt;

    @Column(name = "BOOK_TOTAL_PAGE_CNT")
    private int bookTotalPageCnt;

    public static Book toEntity(BookInsUpdDto dto) {
        return Book.builder()
                .bookIsbn(dto.getBookIsbn())
                .bookCategory(BookCategory.selectToEntity(dto.getBookCategory()))
                .bookTitle(dto.getBookTitle())
                .bookAuthor(dto.getBookAuthor())
                .bookPublisher(dto.getBookPublisher())
                .bookDistributor(dto.getBookDistributor())
                .bookPublicationYear(dto.getBookPublicationYear())
                .bookIndex(dto.getBookIndex())
                .bookInt(dto.getBookInt())
                .bookAuthorInt(dto.getBookAuthorInt())
                .bookImage(dto.getBookImage())
                .bookMaxLoanCnt(dto.getBookMaxLoanCnt())
                .bookMaxReserveCnt(dto.getBookMaxReserveCnt())
                .bookTotalPageCnt(dto.getBookTotalPageCnt())
                .build();
    }

    public static Book selectToEntity(BookSelectDto dto) {
        return Book.builder()
                .bookIsbn(dto.getBookIsbn())
                .bookCategory(BookCategory.selectToEntity(dto.getBookCategory()))
                .bookTitle(dto.getBookTitle())
                .bookAuthor(dto.getBookAuthor())
                .bookPublisher(dto.getBookPublisher())
                .bookDistributor(dto.getBookDistributor())
                .bookPublicationYear(dto.getBookPublicationYear())
                .bookIndex(dto.getBookIndex())
                .bookInt(dto.getBookInt())
                .bookAuthorInt(dto.getBookAuthorInt())
                .bookImage(dto.getBookImage())
                .bookMaxLoanCnt(dto.getBookMaxLoanCnt())
                .bookMaxReserveCnt(dto.getBookMaxReserveCnt())
                .bookTotalPageCnt(dto.getBookTotalPageCnt())
                .bookLike(dto.getBookLike())
                .bookLoanCnt(dto.getBookLoanCnt())
                .bookReserveCnt(dto.getBookReserveCnt())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Book book = (Book) object;
        return getBookMaxLoanCnt() == book.getBookMaxLoanCnt() && getBookMaxReserveCnt() == book.getBookMaxReserveCnt() && getBookLike() == book.getBookLike() && getBookLoanCnt() == book.getBookLoanCnt() && getBookReserveCnt() == book.getBookReserveCnt() && getBookTotalPageCnt() == book.getBookTotalPageCnt() && Objects.equals(getBookIsbn(), book.getBookIsbn()) && Objects.equals(getBookCategory(), book.getBookCategory()) && Objects.equals(getBookTitle(), book.getBookTitle()) && Objects.equals(getBookAuthor(), book.getBookAuthor()) && Objects.equals(getBookPublisher(), book.getBookPublisher()) && Objects.equals(getBookDistributor(), book.getBookDistributor()) && Objects.equals(getBookPublicationYear(), book.getBookPublicationYear()) && Objects.equals(getBookIndex(), book.getBookIndex()) && Objects.equals(getBookInt(), book.getBookInt()) && Objects.equals(getBookAuthorInt(), book.getBookAuthorInt()) && Objects.equals(getBookImage(), book.getBookImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookIsbn(), getBookCategory(), getBookTitle(), getBookAuthor(), getBookPublisher(), getBookDistributor(), getBookPublicationYear(), getBookIndex(), getBookInt(), getBookAuthorInt(), getBookImage(), getBookMaxLoanCnt(), getBookMaxReserveCnt(), getBookLike(), getBookLoanCnt(), getBookReserveCnt(), getBookTotalPageCnt());
    }

}
