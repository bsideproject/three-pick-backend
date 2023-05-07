package com.bside.threepick.domain.goal.validator;

import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.dto.request.UpdateGoalRequest;

public interface GoalValidator {

  void findById(Long goalId);

  void createGoal(CreateGoalRequest createGoalRequest);

  void updateGoal(UpdateGoalRequest updateGoalRequest);
}
