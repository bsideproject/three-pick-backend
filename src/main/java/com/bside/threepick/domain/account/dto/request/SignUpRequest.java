package com.bside.threepick.domain.account.dto.request;

import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.entity.AccountStatus;
import com.bside.threepick.domain.account.entity.SignUpType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

  @Email(message = "이메일을 다시한번 확인해주세요.")
  @NotBlank
  private String email;

  @Size(min = 8, max = 20, message = "영문, 숫자를 포함하여 8자~20자의 비밀번호를 입력해주세요.")
  @NotBlank
  private String password;

  @Size(min = 2, max = 16, message = "2~16자 입력해주세요.")
  @NotBlank
  private String nickName;

  public Account createAccount(String encodedPassword) {
    return new Account(email, encodedPassword, nickName, SignUpType.BASIC, AccountStatus.ACTIVE);
  }
}
