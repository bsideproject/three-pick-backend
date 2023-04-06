package com.bside.threepick.domain.account.dto;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeValueRequest {

  private String email;
  @Size(min = 0, max = 100000, message = "0원 ~ 100,000원 까지 입력 가능합니다.")
  private Long timeValue;
}
