package com.cwpark.library.repository.guide.qna;

import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.dto.guide.notify.QNotifyDto;
import com.cwpark.library.data.dto.guide.qna.QQnaDto;
import com.cwpark.library.data.dto.guide.qna.QnaDto;
import com.cwpark.library.data.dto.user.QUserSelectDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.guide.QQna;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.QUser.user;
import static com.cwpark.library.data.entity.guide.QQna.qna;

@RequiredArgsConstructor
public class QnaRepositoryImpl implements QnaRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<QnaDto> searchPage(User user, String frDt, String toDt, Pageable pageable) {
        List<QnaDto> content = queryFactory
                .select(new QQnaDto (
                        qna.qnaId
                        , new QUserSelectDto(
                              qna.user.userId
                            , qna.user.userPassword
                            , qna.user.userName
                            , qna.user.userSex
                            , qna.user.userBirth
                            , qna.user.userAuthority
                            , qna.user.userLoginFailCnt
                            , qna.user.userOauthType
                        )
                        , qna.qnaDate
                        , qna.qnaQuestion
                        , qna.qnaAnswer
                        , qna.qnaAnswerYn
                ))
                .from(qna)
                .where(allCond(user, frDt, toDt))
                .orderBy(qna.qnaId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(qna)
                .from(qna)
                .where(allCond(user, frDt, toDt))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<QnaDto> searchPage(Pageable pageable) {
        List<QnaDto> content = queryFactory
                .select(new QQnaDto (
                        qna.qnaId
                        , new QUserSelectDto(
                        qna.user.userId
                        , qna.user.userPassword
                        , qna.user.userName
                        , qna.user.userSex
                        , qna.user.userBirth
                        , qna.user.userAuthority
                        , qna.user.userLoginFailCnt
                        , qna.user.userOauthType
                )
                        , qna.qnaDate
                        , qna.qnaQuestion
                        , qna.qnaAnswer
                        , qna.qnaAnswerYn
                ))
                .from(qna)
                .orderBy(qna.qnaId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(qna)
                .from(qna)
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression userEq(User user) {
        if(user == null) {
            return null;
        }

        return qna.user.eq(user);
    }

    private BooleanExpression dateBetween(String frDt, String toDt) {
        return qna.qnaDate.between(frDt, toDt);
    }

    private BooleanExpression allCond(User user, String frDt, String toDt) {
        return dateBetween(frDt, toDt).and(userEq(user));
    }

}
