package com.cwpark.library.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "LOGIN_TOKEN")
public class RememberMeToken extends BaseEntity implements Serializable {
  @Id
  @Column(name = "LOGIN_TOKEN_SERIES")
  private String loginTokenSeries;

  @Column(name = "LOGIN_TOKEN_USERNAME")
  private String loginTokenUserName;

  @Column(name = "LOGIN_TOKEN")
  private String loginToken;

  @Column(name = "LOGIN_TOKEN_LAST_USED")
  private Date loginTokenLastUsed;

  public RememberMeToken(PersistentRememberMeToken token) {
    this.loginTokenSeries = token.getSeries();
    this.loginTokenUserName = token.getUsername();
    this.loginToken = token.getTokenValue();
    this.loginTokenLastUsed = token.getDate();
  }
}