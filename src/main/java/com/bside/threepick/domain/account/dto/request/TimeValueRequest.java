package com.bside.threepick.domain.account.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class TimeValueRequest {

  @NotNull
  private Long accountId;

  @Min(value = 0, message = "0원 ~ 100,000원 까지 입력 가능합니다.")
  @Max(value = 100000, message = "0원 ~ 100,000원 까지 입력 가능합니다.")
  @NotNull
  private Long timeValue;
}
