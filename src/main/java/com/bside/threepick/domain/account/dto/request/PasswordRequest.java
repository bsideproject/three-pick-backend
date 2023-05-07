package com.bside.threepick.domain.account.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class PasswordRequest {

  @NotNull
  private Long accountId;

  @Size(min = 8, max = 20, message = "영문, 숫자를 포함하여 8자~20자의 비밀번호를 입력해주세요.")
  @NotBlank
  private String password;

  @Size(min = 8, max = 20, message = "영문, 숫자를 포함하여 8자~20자의 비밀번호를 입력해주세요.")
  @NotBlank
  private String newPassword;
}
