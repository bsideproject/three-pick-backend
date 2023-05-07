package com.bside.threepick.domain.goal.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class GoalRewardResponse {

  private int doneValue;
  private int missValue;
  private int threePickCount;
  private int doneHour;
  private Map<String, List<GoalDayResponse>> doneGoalWithDays;

  public GoalRewardResponse(List<GoalDayResponse> goals) {
    this.doneValue = makeDoneValue(goals);
    this.missValue = makeTotalValue(goals) - doneValue;
    this.doneHour = makeDoneHour(goals);
    this.doneGoalWithDays = makeDoneGoalWithDays(goals);
    this.threePickCount = makeThreePickCount(doneGoalWithDays);
  }

  private int makeDoneHour(List<GoalDayResponse> goals) {
    int doneHour = goals.stream()
        .filter(GoalDayResponse::isDone)
        .mapToInt(goal -> goal.getHour() * 60 + goal.getMinute()).sum();
    doneHour = new BigDecimal((double) doneHour / 60).setScale(0, RoundingMode.HALF_UP)
        .intValue();
    return doneHour;
  }

  private int makeThreePickCount(Map<String, List<GoalDayResponse>> doneGoalWithDays) {
    int threePickCount = 0;
    for (String localDate : doneGoalWithDays.keySet()) {
      if (doneGoalWithDays.get(localDate).size() > 2) {
        threePickCount++;
      }
    }
    return threePickCount;
  }

  private Map<String, List<GoalDayResponse>> makeDoneGoalWithDays(List<GoalDayResponse> goals) {
    Map<String, List<GoalDayResponse>> doneGoalWithDays = new HashMap<>();
    goals.stream()
        .filter(GoalDayResponse::isDone)
        .forEach(goal ->
        {
          List<GoalDayResponse> goalsOfDate = doneGoalWithDays
              .getOrDefault(goal.getGoalDate().toString(), new ArrayList<>());
          goalsOfDate.add(goal);
          doneGoalWithDays.put(goal.getGoalDate().toString(), goalsOfDate);
        });
    return doneGoalWithDays;
  }

  private int makeDoneValue(List<GoalDayResponse> goals) {
    int doneValue = goals.stream()
        .filter(GoalDayResponse::isDone)
        .mapToInt(goal -> goal.getValue())
        .sum();
    return doneValue;
  }

  private int makeTotalValue(List<GoalDayResponse> goals) {
    return goals.stream()
        .mapToInt(goal -> goal.getValue())
        .sum();
  }
}
