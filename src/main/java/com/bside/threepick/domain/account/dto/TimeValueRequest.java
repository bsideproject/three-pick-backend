package com.bside.threepick.domain.account.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeValueRequest {

  private String email;

  @Min(value = 0, message = "0원 ~ 100,000원 까지 입력 가능합니다.")
  @Max(value = 100000, message = "0원 ~ 100,000원 까지 입력 가능합니다.")
  private Long timeValue;
}
