package com.bside.threepick.domain.goal.mapper;

import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.entity.Goal;

public interface GoalMapper {

  Goal findById(Long goalId);

  Goal createGoal(CreateGoalRequest createGoalRequest);
}
