package com.cwpark.library.data.dto.guide.notify;

import com.cwpark.library.data.entity.guide.Notify;
import com.cwpark.library.data.enums.NotifyType;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotifyFormDto {

    private String type;

    private Long notifyId;

    @NotBlank(message = "타입은 필수 입력 사항입니다")
    private String notifyType;

    @NotBlank(message = "제목은 필수 입력 사항입니다")
    private String notifyTitle;

    @NotBlank(message = "내용은 필수 입력 사항입니다")
    private String notifyText;

    private String notifyImg;

    @NotBlank(message = "시작일자는 필수 입력 사항입니다")
    private String notifyStartDt;

    @NotBlank(message = "종료일자는 필수 입력 사항입니다")
    private String notifyEndDt;

    private MultipartFile multipartFile;
}
