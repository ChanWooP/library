package com.cwpark.library.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "SETTING")
public class Setting extends BaseEntity{
    @Id
    @Column(name = "SET_ID")
    private String setId;

    @Column(name = "SET_NAME")
    private String setName;

    @Column(name = "SET_TYPE")
    private String setType;

    @Column(name = "SET_VALUE")
    private String setValue;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Setting setting = (Setting) object;
        return Objects.equals(getSetId(), setting.getSetId()) && Objects.equals(getSetName(), setting.getSetName()) && Objects.equals(getSetValue(), setting.getSetValue()) && Objects.equals(getSetType(), setting.getSetType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSetId(), getSetName(), getSetValue(), getSetType());
    }
}
