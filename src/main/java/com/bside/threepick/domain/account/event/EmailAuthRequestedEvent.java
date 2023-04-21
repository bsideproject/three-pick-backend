package com.bside.threepick.domain.account.event;

public class EmailAuthRequestedEvent {

  private String email;

  private EmailAuthRequestedEvent() {
  }

  public EmailAuthRequestedEvent(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
