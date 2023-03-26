package com.bside.threepick.domain.account.dto;

import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.entity.SignUpType;
import com.bside.threepick.exception.SignUpPasswordException;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

  @Email(message = "이메일을 다시한번 확인해주세요.")
  private String email;
  @Size(min = 8, max = 20, message = "영문, 숫자를 포함하여 8자~20자의 비밀번호를 입력해주세요.")
  private String password;
  @Size(min = 8, max = 20, message = "영문, 숫자를 포함하여 8자~20자의 비밀번호를 입력해주세요.")
  private String passwordCheck;
  @Size(min = 2, max = 16, message = "2~16자 입력해주세요.")
  private String nickName;

  public Account createAccount(String encodedPassword) {
    if (isNotSamePasswordAndPasswordCheck()) {
      throw new SignUpPasswordException("비밀번호와 비밀번호확인의 값이 일치하지 않습니다.");
    }
    return new Account(email, encodedPassword, nickName, SignUpType.BASIC);
  }

  private boolean isNotSamePasswordAndPasswordCheck() {
    return !password.equals(passwordCheck);
  }
}
