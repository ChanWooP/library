package com.cwpark.library.data.dto.guide.notify;

import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.book.BookHope;
import com.cwpark.library.data.entity.guide.Notify;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.cwpark.library.data.enums.NotifyType;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class NotifyDto {

    private Long notifyId;

    @NotBlank(message = "타입은 필수 입력 사항입니다")
    private NotifyType notifyType;

    @NotBlank(message = "제목은 필수 입력 사항입니다")
    private String notifyTitle;

    @NotBlank(message = "내용은 필수 입력 사항입니다")
    private String notifyText;

    private String notifyImg;

    @NotBlank(message = "시작일자는 필수 입력 사항입니다")
    private String notifyStartDt;

    @NotBlank(message = "종료일자는 필수 입력 사항입니다")
    private String notifyEndDt;

    @QueryProjection
    public NotifyDto(Long notifyId, NotifyType notifyType, String notifyTitle, String notifyText, String notifyImg, String notifyStartDt, String notifyEndDt) {
        this.notifyId = notifyId;
        this.notifyType = notifyType;
        this.notifyTitle = notifyTitle;
        this.notifyText = notifyText;
        this.notifyImg = notifyImg;
        this.notifyStartDt = notifyStartDt;
        this.notifyEndDt = notifyEndDt;
    }

    public static NotifyDto toDto(Notify notify) {
        return NotifyDto.builder()
                .notifyId(notify.getNotifyId())
                .notifyType(notify.getNotifyType())
                .notifyTitle(notify.getNotifyTitle())
                .notifyText(notify.getNotifyText())
                .notifyImg(notify.getNotifyImg())
                .notifyStartDt(notify.getNotifyStartDt())
                .notifyEndDt(notify.getNotifyEndDt())
                .build();
    }

    public static NotifyDto formToDto(NotifyFormDto dto) {
        return NotifyDto.builder()
                .notifyId(dto.getNotifyId())
                .notifyType(dto.getNotifyType())
                .notifyTitle(dto.getNotifyTitle())
                .notifyText(dto.getNotifyText())
                .notifyImg(dto.getNotifyImg())
                .notifyStartDt(dto.getNotifyStartDt())
                .notifyEndDt(dto.getNotifyEndDt())
                .build();
    }
}
