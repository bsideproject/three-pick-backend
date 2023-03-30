package com.bside.threepick.domain.account.entity;


import com.bside.threepick.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
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

  @Column(name = "nick_name", nullable = true, updatable = true)
  private String nickName;

  @Column(name = "time_value", nullable = true, updatable = true)
  private int timeValue;

  @Column(name = "signup_type", nullable = false, updatable = true)
  @Enumerated(value = EnumType.STRING)
  private SignUpType signUpType;

  protected Account() {
  }

  public Account(String email, String password, String nickName, SignUpType signUpType) {
    this.email = email;
    this.password = password;
    this.nickName = nickName;
    this.signUpType = signUpType;
  }

  public void changeTimeValue(int timeValue) {
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

  public int getTimeValue() {
    return timeValue;
  }

}
