package com.cwpark.library.data.dto;

import com.cwpark.library.data.entity.Setting;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingDto {
    private String setId;
    private String setName;
    private String setType;
    private String setValue;

    public static SettingDto toDto(Setting setting) {
        return SettingDto.builder()
                .setId(setting.getSetId())
                .setName(setting.getSetName())
                .setType(setting.getSetType())
                .setValue(setting.getSetValue())
                .build();
    }

    public Object getTypeConversionValue() {
        if(Objects.equals(this.setType, "INTEGER")) {
            return Integer.valueOf(this.setValue);
        } else {
            return this.setValue;
        }
    }
}
