package com.cwpark.library.repository.guide.notify;

import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.dto.guide.notify.QNotifyDto;
import com.cwpark.library.data.entity.guide.QNotify;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cwpark.library.data.entity.guide.QNotify.notify;

@RequiredArgsConstructor
public class NotifyRepositoryImpl implements NotifyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<NotifyDto> searchPage(String search, String nowDate, Pageable pageable) {
        List<NotifyDto> content = queryFactory
                .select(new QNotifyDto(
                        notify.notifyId
                        , notify.notifyType
                        , notify.notifyTitle
                        , notify.notifyText
                        , notify.notifyImg
                        , notify.notifyStartDt
                        , notify.notifyEndDt
                ))
                .from(notify)
                .where(allCond(nowDate, search))
                .orderBy(notify.notifyStartDt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(notify)
                .from(notify)
                .where(allCond(nowDate, search))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression dateBetween(String nowDate) {
        return notify.notifyStartDt.loe(nowDate).and(notify.notifyEndDt.goe(nowDate));
    }

    private BooleanExpression titleContains(String search) {
        if(search == null) {
            return null;
        }

        return notify.notifyTitle.contains(search);
    }

    private BooleanExpression allCond(String nowDate, String search) {
        return dateBetween(nowDate).and(titleContains(search));
    }

}