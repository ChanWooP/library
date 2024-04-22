package com.cwpark.library.repository.book.category;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.BookCategorySearchDto;
import com.cwpark.library.data.dto.book.category.QBookCategoryDto;
import com.cwpark.library.data.dto.book.category.QBookCategorySearchDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.book.QBookCategory.bookCategory;
import static com.cwpark.library.data.entity.book.QBook.book;

@RequiredArgsConstructor
public class BookCategoryRepositoryImpl implements BookCategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<BookCategoryDto> searchPage(Pageable pageable) {
        List<BookCategoryDto> content = queryFactory
                .select(new QBookCategoryDto(
                        bookCategory.categoryId
                        , bookCategory.categoryName
                ))
                .from(bookCategory)
                .orderBy(bookCategory.categoryId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(bookCategory)
                .from(bookCategory)
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * @return
     */
    @Override
    public List<BookCategorySearchDto> searchCategory() {
        return queryFactory
                .select(new QBookCategorySearchDto(
                        bookCategory.categoryId
                        , bookCategory.categoryName
                        , ExpressionUtils.as(
                        JPAExpressions
                                .select(book.bookIsbn.count())
                                .from(book)
                                .where(book.bookCategory.categoryId.eq(bookCategory.categoryId)), "count")
                ))
                .from(bookCategory)
                .groupBy(bookCategory.categoryId, bookCategory.categoryName)
                .orderBy(bookCategory.categoryId.desc())
                .fetch();
    }
}
