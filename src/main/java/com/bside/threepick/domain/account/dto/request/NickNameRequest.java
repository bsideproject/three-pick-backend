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
public class NickNameRequest {

  @NotNull
  private Long accountId;

  @Size(min = 2, max = 16, message = "2~16자 입력해주세요.")
  @NotBlank
  private String nickName;
}
