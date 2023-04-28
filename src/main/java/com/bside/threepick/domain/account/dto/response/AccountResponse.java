package com.bside.threepick.domain.account.dto.response;

import com.bside.threepick.domain.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class AccountResponse {

  private Long accountId;
  private String email;
  private String nickName;
  private Long timeValue;
  private int changeCount;
  private boolean coachMark;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime lastLoginDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime modifiedDate;

  public static AccountResponse of(Account account) {
    return new AccountResponse(account.getId(), account.getEmail(), account.getNickName(), account.getTimeValue(),
        account.getChangeCount(), account.isCoachMark(), account.getLastLoginDate(), account.getCreatedDate(),
        account.getModifiedDate());
  }
}
