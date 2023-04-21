package com.bside.threepick.domain.goal.dto.request;

import com.bside.threepick.domain.goal.entity.GoalStatus;
import com.bside.threepick.domain.goal.entity.Weight;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UpdateGoalRequest {

  private Long accountId;
  private Long goalId;

  @NotBlank(message = "content 값은 필수에요.")
  private String content;
  private int hour;
  private int minute;
  private Weight weight;
  private GoalStatus goalStatus;
}
