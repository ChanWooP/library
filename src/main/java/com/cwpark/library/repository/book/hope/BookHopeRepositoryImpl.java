package com.cwpark.library.repository.book.hope;

import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.dto.book.QBookHopeDto;
import com.cwpark.library.data.dto.book.category.QBookCategoryDto;
import com.cwpark.library.data.dto.user.QUserSelectDto;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.QUser.user;
import static com.cwpark.library.data.entity.book.QBookHope.bookHope;

@RequiredArgsConstructor
public class BookHopeRepositoryImpl implements BookHopeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<BookHopeDto> searchPage(BookHopeStatus bookHopeStatus, String frDt, String toDt, String search, Pageable pageable) {
        List<BookHopeDto> content = queryFactory
                .select(new QBookHopeDto(
                        bookHope.hopeId
                        , new QUserSelectDto(
                            bookHope.user.userId
                            , bookHope.user.userPassword
                            , bookHope.user.userName
                            , bookHope.user.userSex
                            , bookHope.user.userBirth
                            , bookHope.user.userAuthority
                            , bookHope.user.userLoginFailCnt
                            , bookHope.user.userOauthType
                        )
                        , bookHope.hopeIsbn
                        , bookHope.hopTitle
                        , bookHope.hopeAuthor
                        , bookHope.hopePublisher
                        , bookHope.hopeDate
                        , bookHope.hopeStatus
                ))
                .from(bookHope)
                .where(allCond(bookHopeStatus, frDt, toDt, search))
                .orderBy(bookHope.hopeDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(bookHope)
                .from(bookHope)
                .where(allCond(bookHopeStatus, frDt, toDt, search))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression statusEq(BookHopeStatus bookHopeStatus) {
        return bookHope.hopeStatus.eq(bookHopeStatus);
    }

    private BooleanExpression dateBetween(String frDt, String toDt) {
        return bookHope.hopeDate.between(frDt, toDt);
    }

    private BooleanExpression titleAndIsbnContains(String search) {
        if(search == null) {
            return null;
        }

        return bookHope.hopTitle.contains(search).or(bookHope.hopeIsbn.contains(search));
    }

    private BooleanExpression allCond(BookHopeStatus bookHopeStatus, String frDt, String toDt, String search) {
        return statusEq(bookHopeStatus).and(dateBetween(frDt, toDt)).and(titleAndIsbnContains(search));
    }

}
