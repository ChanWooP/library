package com.cwpark.library.data.entity.guide;

import com.cwpark.library.data.dto.guide.notify.NotifyDto;
import com.cwpark.library.data.entity.BaseEntity;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.enums.BookHopeStatus;
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
@Table(name = "NOTIFY")
public class Notify extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFY_ID")
    private Long notifyId;

    @Column(name = "NOTIFY_TYPE")
    @Enumerated(EnumType.STRING)
    private NotifyType notifyType;

    @Column(name = "NOTIFY_TITLE")
    private String notifyTitle;

    @Column(name = "NOTIFY_TEXT")
    private String notifyText;

    @Column(name = "NOTIFY_IMG")
    private String notifyImg;

    @Column(name = "NOTIFY_START_DT")
    private String notifyStartDt;

    @Column(name = "NOTIFY_END_DT")
    private String notifyEndDt;

    public static Notify toEntity(NotifyDto notifyDto) {
        return Notify.builder()
                .notifyId(notifyDto.getNotifyId())
                .notifyType(notifyDto.getNotifyType())
                .notifyTitle(notifyDto.getNotifyTitle())
                .notifyText(notifyDto.getNotifyText())
                .notifyImg(notifyDto.getNotifyImg())
                .notifyStartDt(notifyDto.getNotifyStartDt())
                .notifyEndDt(notifyDto.getNotifyEndDt())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Notify notify = (Notify) object;
        return Objects.equals(getNotifyId(), notify.getNotifyId()) && Objects.equals(getNotifyType(), notify.getNotifyType()) && Objects.equals(getNotifyTitle(), notify.getNotifyTitle()) && Objects.equals(getNotifyText(), notify.getNotifyText()) && Objects.equals(getNotifyImg(), notify.getNotifyImg()) && Objects.equals(getNotifyStartDt(), notify.getNotifyStartDt()) && Objects.equals(getNotifyEndDt(), notify.getNotifyEndDt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNotifyId(), getNotifyType(), getNotifyTitle(), getNotifyText(), getNotifyImg(), getNotifyStartDt(), getNotifyEndDt());
    }
}
