package com.bside.threepick.domain.account.dto;

import com.bside.threepick.domain.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountResponse {

  private String email;
  private String nickName;
  private Long timeValue;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private Instant createdDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private Instant modifiedDate;

  public static AccountResponse of(Account account) {
    return new AccountResponse(account.getEmail(), account.getNickName(), account.getTimeValue(),
        account.getCreatedDate(), account.getModifiedDate());
  }
}
