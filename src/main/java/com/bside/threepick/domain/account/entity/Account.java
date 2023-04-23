package com.bside.threepick.domain.account.entity;


import com.bside.threepick.common.BaseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

  @Column(name = "next_time_value_date", nullable = false, updatable = true)
  private LocalDate nextTimeValueDate;

  @Column(name = "change_count", nullable = false, updatable = true)
  @ColumnDefault("0")
  private int changeCount;

  @Column(name = "signup_type", nullable = false, updatable = true)
  @Enumerated(value = EnumType.STRING)
  private SignUpType signUpType;

  @Column(name = "status", nullable = false, updatable = true)
  @Enumerated(value = EnumType.STRING)
  private AccountStatus status;

  @Column(name = "coach_mark", nullable = false, updatable = true)
  @ColumnDefault("false")
  private boolean coachMark;

  @Column(name = "last_login_date", nullable = true, updatable = true)
  private LocalDateTime lastLoginDate;

  protected Account() {
  }

  public Account(String email, String password, String nickName, SignUpType signUpType, AccountStatus status) {
    this.email = email;
    this.password = password;
    this.nickName = nickName;
    this.signUpType = signUpType;
    this.status = status;
    this.nextTimeValueDate = LocalDate.now();
  }

  public void changeLastLoginDate() {
    lastLoginDate = LocalDateTime.now();
  }

  public void changeTimeValue(Long timeValue) {
    this.timeValue = timeValue;
    changeCount++;
  }

  public void changeNextTimeValue(Long timeValue) {
    nextTimeValue = timeValue;
    nextTimeValueDate = LocalDate.now()
        .plusDays(1l);
    changeCount++;
  }

  public void changeCoachMark() {
    coachMark = true;
  }

  public void checkNextTimeValue() {
    resetChangeCount();
    resetNextTimeValue();
  }

  private void resetChangeCount() {
    if (LocalDate.now().isAfter(nextTimeValueDate)) {
      changeCount = 0;
    }
  }

  private void resetNextTimeValue() {
    if (nextTimeValue != 0 &&
        (LocalDate.now().isEqual(nextTimeValueDate) || LocalDate.now().isAfter(nextTimeValueDate))) {
      timeValue = nextTimeValue;
      nextTimeValue = 0L;
    }
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

  public int getChangeCount() {
    return changeCount;
  }

  public LocalDateTime getLastLoginDate() {
    return lastLoginDate;
  }

  public LocalDate getNextTimeValueDate() {
    return nextTimeValueDate;
  }

  public boolean isCoachMark() {
    return coachMark;
  }

  public boolean isBasicOfSignUpType() {
    return signUpType == SignUpType.BASIC;
  }
}
