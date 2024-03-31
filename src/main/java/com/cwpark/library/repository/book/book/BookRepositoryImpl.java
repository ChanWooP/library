package com.cwpark.library.repository.book.book;

import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.book.QBookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.book.QBook.book;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<BookSelectDto> searchPage(String title, Pageable pageable) {
        List<BookSelectDto> content = queryFactory
                .select(new QBookSelectDto(
                        book.bookIsbn
                        ,book.bookCategory
                        ,book.bookTitle
                        ,book.bookAuthor
                        ,book.bookPublisher
                        ,book.bookDistributor
                        ,book.bookPublicationYear
                        ,book.bookIndex
                        ,book.bookInt
                        ,book.bookAuthorInt
                        ,book.bookImage
                        ,book.bookMaxLoanCnt
                        ,book.bookMaxReserveCnt
                        ,book.bookLike
                        ,book.bookLoanCnt
                        ,book.bookReserveCnt
                ))
                .from(book)
                .where(book.bookTitle.like(title))
                .orderBy(book.bookTitle.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(book)
                .from(book)
                .where(book.bookTitle.like(title))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }
}
