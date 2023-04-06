package com.bside.threepick.domain.account.dto;

import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.entity.SignUpType;
import com.bside.threepick.domain.account.entity.Status;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

  @Email(message = "이메일을 다시한번 확인해주세요.")
  private String email;
  @Size(min = 8, max = 20, message = "영문, 숫자를 포함하여 8자~20자의 비밀번호를 입력해주세요.")
  private String password;
  @Size(min = 2, max = 16, message = "2~16자 입력해주세요.")
  private String nickName;

  public Account createAccount(String encodedPassword) {
    return new Account(email, encodedPassword, nickName, SignUpType.BASIC, Status.ACTIVE);
  }
}
