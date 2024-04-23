package com.cwpark.library.data.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLikeId implements Serializable {
    private String user;
    private String book;
}
