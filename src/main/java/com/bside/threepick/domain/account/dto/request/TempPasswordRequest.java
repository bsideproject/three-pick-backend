package com.bside.threepick.domain.account.dto.request;

import javax.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class TempPasswordRequest {

  @Email(message = "이메일을 다시한번 확인해주세요.")
  private String email;
}
