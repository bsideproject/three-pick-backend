package com.bside.threepick.domain.goal.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class GoalDayResponses {

  private Long accountId;
  private Long timeValue;
  private int totalValue;
  private int doneValue;
  private List<GoalDayResponse> goalResponses;

  public GoalDayResponses(Long accountId, List<GoalDayResponse> goalResponses) {
    this.accountId = accountId;
    this.timeValue = goalResponses.get(0)
        .getTimeValue();
    this.goalResponses = goalResponses;
    this.totalValue = makeTotalValue();
    this.doneValue = makeDoneValue();
  }

  private int makeDoneValue() {
    return goalResponses.stream()
        .filter(GoalDayResponse::isDone)
        .mapToInt(GoalDayResponse::getValue)
        .sum();
  }

  private int makeTotalValue() {
    return goalResponses.stream()
        .mapToInt(GoalDayResponse::getValue)
        .sum();
  }
}
