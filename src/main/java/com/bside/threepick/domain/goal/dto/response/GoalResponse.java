package com.bside.threepick.domain.goal.dto.response;

import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.entity.GoalStatus;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class GoalResponse {

  private Long id;
  private Long accountId;
  private String content;
  private BigDecimal totalValue;
  private GoalStatus goalStatus;
  private int hour;
  private int minute;
  private Double weight;

  public static GoalResponse of(Goal goal) {
    return new GoalResponse(goal.getId(), goal.getAccountId(), goal.getContent(), goal.getTotalValue().getValue(),
        goal.getGoalStatus(), goal.getHour(), goal.getMinute(), goal.getWeight().getValue());
  }
}
