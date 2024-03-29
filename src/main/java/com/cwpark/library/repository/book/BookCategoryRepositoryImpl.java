package com.cwpark.library.repository.book;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.dto.book.category.QBookCategoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.book.QBookCategory.bookCategory;

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

}
