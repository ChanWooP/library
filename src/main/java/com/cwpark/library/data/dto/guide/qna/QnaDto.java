package com.cwpark.library.data.dto.guide.qna;

import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.BaseEntity;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.guide.Qna;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class QnaDto extends BaseEntity {

    private Long qnaId;

    @NotBlank(message = "사용자는 필수 입력 사항입니다")
    private UserSelectDto user;

    @NotBlank(message = "날짜는 필수 입력 사항입니다")
    private String qnaDate;

    private String qnaQuestion;

    private String qnaAnswer;

    private String qnaAnswerYn;

    public static QnaDto toDto(Qna qna) {
        return QnaDto.builder()
                .qnaId(qna.getQnaId())
                .user(UserSelectDto.toDto(qna.getUser()))
                .qnaQuestion(qna.getQnaQuestion())
                .qnaAnswer(qna.getQnaAnswer())
                .qnaAnswerYn(qna.getQnaAnswerYn())
                .build();
    }

    public static QnaDto formToDto(QnaFormDto qna) {
        return QnaDto.builder()
                .qnaId(qna.getQnaId())
                .user(qna.getUser())
                .qnaDate(qna.getQnaDate())
                .qnaQuestion(qna.getQnaQuestion())
                .qnaAnswer(qna.getQnaAnswer())
                .qnaAnswerYn(qna.getQnaAnswerYn())
                .build();
    }

    @QueryProjection
    public QnaDto(Long qnaId, UserSelectDto user, String qnaDate, String qnaQuestion, String qnaAnswer, String qnaAnswerYn) {
        this.qnaId = qnaId;
        this.user = user;
        this.qnaDate = qnaDate;
        this.qnaQuestion = qnaQuestion;
        this.qnaAnswer = qnaAnswer;
        this.qnaAnswerYn = qnaAnswerYn;
    }
}
