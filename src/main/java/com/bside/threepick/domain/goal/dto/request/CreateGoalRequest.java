package com.bside.threepick.domain.goal.dto.request;

import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.entity.GoalType;
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
public class CreateGoalRequest {

  @NotNull
  private Long accountId;

  @NotBlank
  private String content;
  private int hour;
  private int minute;
  private Weight weight;
  private GoalType goalType;

  public Goal createGoal(Long timeValue) {
    return new Goal(accountId, content, timeValue, hour, minute, weight, goalType);
  }
}
