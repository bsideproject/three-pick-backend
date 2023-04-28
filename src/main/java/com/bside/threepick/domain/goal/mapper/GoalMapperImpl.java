package com.bside.threepick.domain.goal.mapper;

import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.reposiroty.GoalRepository;
import com.bside.threepick.domain.goal.validator.GoalValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoalMapperImpl implements GoalMapper {

  private final GoalRepository goalRepository;
  private final GoalValidator goalValidator;
  private final AccountService accountService;

  @Override
  public Goal findById(Long goalId) {
    goalValidator.findById(goalId);
    return goalRepository.findById(goalId)
        .get();
  }

  @Override
  public Goal createGoal(CreateGoalRequest createGoalRequest) {
    goalValidator.createGoal(createGoalRequest);
    Long timeValue = accountService.findAccountResponseById(createGoalRequest.getAccountId())
        .getTimeValue();
    return goalRepository.save(createGoalRequest.createGoal(timeValue));
  }
}
