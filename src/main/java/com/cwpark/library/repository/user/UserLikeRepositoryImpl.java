package com.cwpark.library.repository.user;

import com.cwpark.library.data.dto.book.book.QBookSelectDto;
import com.cwpark.library.data.dto.book.category.QBookCategoryDto;
import com.cwpark.library.data.dto.user.QUserLikeDto;
import com.cwpark.library.data.dto.user.QUserSelectDto;
import com.cwpark.library.data.dto.user.UserLikeDto;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.QUser.user;
import static com.cwpark.library.data.entity.book.QBook.book;
import static com.cwpark.library.data.entity.QUserLike.userLike;

@RequiredArgsConstructor
public class UserLikeRepositoryImpl implements UserLikeCustomRepository{
    private final JPAQueryFactory queryFactory;
    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<UserLikeDto> searchPage(Pageable pageable, String userId) {
        List<UserLikeDto> content = queryFactory
                .select(new QUserLikeDto(
                    new QUserSelectDto(
                            user.userId
                            , user.userPassword
                            , user.userName
                            , user.userSex
                            , user.userBirth
                            , user.userAuthority
                            , user.userLoginFailCnt
                            , user.userOauthType
                    )
                    , new QBookSelectDto(
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
                    )
                ))
                .from(userLike)
                .where(userLike.user.userId.eq(userId))
                .orderBy(userLike.createdDate.desc())
                .fetch();

        long total = queryFactory
                .select(userLike)
                .from(userLike)
                .where(userLike.user.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }
}
