package com.bside.threepick.domain.goal.dto.response;

import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.entity.GoalStatus;
import com.bside.threepick.domain.goal.entity.GoalType;
import com.bside.threepick.domain.goal.entity.Weight;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class GoalDayResponse {

  private Long goalId;
  private Long accountId;
  private Long timeValue;
  private String content;
  private int value;
  private GoalStatus goalStatus;
  private int hour;
  private int minute;
  private Weight weight;
  private GoalType goalType;
  private LocalDate goalDate;

  public static GoalDayResponse of(Goal goal) {

    return new GoalDayResponse(goal.getId(), goal.getAccountId(), goal.getTimeValue(), goal.getContent(),
        goal.getValue().value(), goal.getGoalStatus(), goal.getHour(), goal.getMinute(), goal.getWeight(),
        goal.getGoalType(), goal.getGoalDate());
  }

  @JsonIgnore
  public boolean isDone() {
    return goalStatus.isDone();
  }
}
