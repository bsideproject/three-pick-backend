package com.bside.threepick.domain.goal.mapper;

import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.dto.response.GoalDayResponses;
import com.bside.threepick.domain.goal.entity.Goal;
import java.time.LocalDate;

public interface GoalMapper {

  Goal findById(Long goalId);

  Goal createGoal(CreateGoalRequest createGoalRequest);

  GoalDayResponses findGoalsByAccountIdAndDate(Long accountId, LocalDate date);
}
