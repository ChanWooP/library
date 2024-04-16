package com.cwpark.library.data.dto.guide.qna;

import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.BaseEntity;
import com.cwpark.library.data.entity.guide.Qna;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaFormDto extends BaseEntity {

    private Long qnaId;

    private String userId;

    private UserSelectDto user;

    private String qnaDate;

    private String qnaQuestion;

    private String qnaAnswer;

    private String qnaAnswerYn;

}
