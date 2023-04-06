package com.bside.threepick.domain.account.entity;


import com.bside.threepick.common.BaseEntity;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Table(name = "account"
    , indexes = @Index(name = "UK_ACCOUNT_EMAIL", columnList = "email", unique = true)
)
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", nullable = false, updatable = false)
  private String email;

  @Column(name = "password", nullable = true, updatable = true)
  private String password;

  @Column(name = "nick_name", nullable = false, updatable = true)
  private String nickName;

  @Column(name = "time_value", nullable = true, updatable = true)
  private Long timeValue;

  @Column(name = "next_time_value", nullable = true, updatable = true)
  @ColumnDefault("0")
  private Long nextTimeValue;

  @Column(name = "next_time_value_date", nullable = true, updatable = true)
  private Instant nextTimeValueDate;

  @Column(name = "change_count", nullable = true, updatable = true)
  @ColumnDefault("0")
  private int changeCount;

  @Column(name = "signup_type", nullable = false, updatable = true)
  @Enumerated(value = EnumType.STRING)
  private SignUpType signUpType;

  @Column(name = "status", nullable = false, updatable = true)
  @Enumerated(value = EnumType.STRING)
  private Status status;

  protected Account() {
  }

  public Account(String email, String password, String nickName, SignUpType signUpType, Status status) {
    this.email = email;
    this.password = password;
    this.nickName = nickName;
    this.signUpType = signUpType;
    this.status = status;
  }

  public void changeTimeValue(Long timeValue) {
    this.timeValue = timeValue;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getNickName() {
    return nickName;
  }

  public String getPassword() {
    return password;
  }

  public Long getTimeValue() {
    return timeValue;
  }

}
