package com.bside.threepick.domain.account.dto;

import com.bside.threepick.domain.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountResponse {

  private String email;
  private String nickName;
  private int timeValue;
  // TODO: KST로 변경해야됨
  private long createdDate;
  private long modifiedDate;

  public static AccountResponse of(Account account) {
    return new AccountResponse(account.getEmail(), account.getNickName(), account.getTimeValue(),
        account.getCreatedDate().toEpochMilli(), account.getModifiedDate().toEpochMilli());
  }
}
