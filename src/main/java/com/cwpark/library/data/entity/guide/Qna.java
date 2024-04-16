package com.cwpark.library.data.entity.guide;

import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.dto.guide.qna.QnaDto;
import com.cwpark.library.data.entity.BaseEntity;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.NotifyType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "QNA")
public class Qna extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QNA_ID")
    private Long qnaId;

    @ManyToOne
    @JoinColumn(name = "QNA_USER")
    private User user;

    @Column(name = "QNA_DATE")
    private String qnaDate;

    @Column(name = "QNA_QUESTION")
    private String qnaQuestion;

    @Column(name = "QNA_ANSWER")
    private String qnaAnswer;

    @Column(name = "QNA_ANSWER_YN")
    private String qnaAnswerYn;

    public static Qna toEntity(QnaDto dto) {
        return Qna.builder()
                .qnaId(dto.getQnaId())
                .user(User.selectToEntity(dto.getUser()))
                .qnaDate(dto.getQnaDate())
                .qnaQuestion(dto.getQnaQuestion())
                .qnaAnswer(dto.getQnaAnswer())
                .qnaAnswerYn(dto.getQnaAnswerYn())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Qna qna = (Qna) object;
        return Objects.equals(getQnaId(), qna.getQnaId()) && Objects.equals(getUser(), qna.getUser()) && Objects.equals(getQnaDate(), qna.getQnaDate()) && Objects.equals(getQnaQuestion(), qna.getQnaQuestion()) && Objects.equals(getQnaAnswer(), qna.getQnaAnswer()) && Objects.equals(getQnaAnswerYn(), qna.getQnaAnswerYn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQnaId(), getUser(), getQnaDate(), getQnaQuestion(), getQnaAnswer(), getQnaAnswerYn());
    }
}
