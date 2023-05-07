package com.bside.threepick.domain.account.event;

public class TempPasswordRequestedEvent {

  private String email;
  private String password;

  private TempPasswordRequestedEvent() {
  }

  public TempPasswordRequestedEvent(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
