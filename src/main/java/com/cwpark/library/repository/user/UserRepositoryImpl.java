package com.cwpark.library.repository.user;

import com.cwpark.library.data.dto.book.category.QBookCategoryDto;
import com.cwpark.library.data.dto.user.QUserSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<UserSelectDto> searchPage(String search, Pageable pageable) {
        List<UserSelectDto> content = queryFactory
                .select(new QUserSelectDto(
                        user.userId
                        , user.userPassword
                        , user.userName
                        , user.userSex
                        , user.userBirth
                        , user.userAuthority
                        , user.userLoginFailCnt
                        , user.userOauthType
                ))
                .from(user)
                .where(user.userId.contains(search).or(user.userName.contains(search)))
                .orderBy(user.userId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(user)
                .from(user)
                .where(user.userId.contains(search).or(user.userName.contains(search)))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }
}
