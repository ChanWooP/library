package com.cwpark.library.repository.book.book;

import com.cwpark.library.data.dto.book.book.BookSelectDto;
import com.cwpark.library.data.dto.book.book.QBookSelectDto;
import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.QBookCategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.book.QBook.book;
import static com.cwpark.library.data.entity.guide.QQna.qna;

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
                        , new QBookCategoryDto(
                                book.bookCategory.categoryId
                              , book.bookCategory.categoryName
                        )
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
                        ,book.bookTotalPageCnt
                ))
                .from(book)
                .where(book.bookTitle.contains(title))
                .orderBy(book.bookIsbn.asc())
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

    /**
     * @param category
     * @param search
     * @param pageable
     * @return
     */
    @Override
    public Page<BookSelectDto> searchBook(Long category, String search, Pageable pageable) {
        List<BookSelectDto> content = queryFactory
                .select(new QBookSelectDto(
                        book.bookIsbn
                        , new QBookCategoryDto(
                        book.bookCategory.categoryId
                        , book.bookCategory.categoryName
                )
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
                        ,book.bookTotalPageCnt
                ))
                .from(book)
                .where(book.bookTitle.contains(search).or(book.bookIsbn.contains(search)).and(categoryEq(category)))
                .orderBy(book.bookTitle.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(book)
                .from(book)
                .where(book.bookTitle.contains(search).or(book.bookIsbn.contains(search)).and(categoryEq(category)))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression categoryEq(Long category) {
        if(category == null) {
            return null;
        }

        return book.bookCategory.categoryId.eq(category);
    }
}
