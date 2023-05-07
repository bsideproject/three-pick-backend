package com.bside.threepick.domain.account.dto.response;

import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.entity.GoalType;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class GoalMonthResponse {

  private Long goalId;
  private Long accountId;
  private String content;
  private GoalType goalType;
  private YearMonth yearMonth;

  public GoalMonthResponse(Goal goal, YearMonth yearMonth) {
    this.goalId = goal.getId();
    this.accountId = goal.getAccountId();
    this.content = goal.getContent();
    this.goalType = goal.getGoalType();
    this.yearMonth = yearMonth;
  }
}
