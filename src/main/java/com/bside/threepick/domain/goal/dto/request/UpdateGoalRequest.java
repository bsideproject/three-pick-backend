package com.bside.threepick.domain.goal.dto.request;

import com.bside.threepick.domain.goal.entity.GoalStatus;
import com.bside.threepick.domain.goal.entity.Weight;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UpdateGoalRequest {

  @NotNull
  private Long accountId;

  @NotNull
  private Long goalId;

  @NotBlank
  private String content;
  private int hour;
  private int minute;
  private Weight weight;
  private GoalStatus goalStatus;
}
