package com.bside.threepick.domain.goal.validator;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.dto.request.UpdateGoalRequest;
import com.bside.threepick.domain.goal.entity.GoalStatus;
import com.bside.threepick.domain.goal.entity.GoalType;
import com.bside.threepick.domain.goal.entity.Weight;
import com.bside.threepick.domain.goal.reposiroty.GoalRepository;
import com.bside.threepick.exception.CreateGoalException;
import com.bside.threepick.exception.EntityNotFoundException;
import com.bside.threepick.exception.UpdateGoalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoalValidatorImpl implements GoalValidator {

  private final GoalRepository goalRepository;
  private final AccountService accountService;

  @Override
  public void findById(Long goalId) {
    goalRepository.findById(goalId)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.GOAL_NOT_FOUND, "존재하지 않는 목표 데이터에요. goalId: " + goalId));
  }

  @Override
  public void createGoal(CreateGoalRequest createGoalRequest) {
    Long timeValue = accountService.findAccountResponseById(createGoalRequest.getAccountId())
        .getTimeValue();
    if (timeValue == null) {
      throw new CreateGoalException(ErrorCode.TIME_VALUE_NOT_FOUND);
    }

    GoalType goalType = createGoalRequest.getGoalType();
    GoalType.isNullThenThrow(goalType);
    int limitSize = GoalType.TODAY == goalType ? 2 : 0;
    if (goalRepository.findGoalsByAccountIdAndGoalType(createGoalRequest.getAccountId(), goalType)
        .size() > limitSize) {
      throw new CreateGoalException(ErrorCode.GOAL_NOT_CREATED);
    }

    if (goalType.isToday()) {
      if (1 > createGoalRequest.getHour() && createGoalRequest.getMinute() < 1) {
        throw new CreateGoalException(ErrorCode.GOAL_NOT_CREATED, "목표시간을 설정해 주세요.");
      }

      Weight.isNullThenThrow(createGoalRequest.getWeight());
    }
  }

  @Override
  public void updateGoal(UpdateGoalRequest updateGoalRequest) {
    if (1 > updateGoalRequest.getHour() && updateGoalRequest.getMinute() < 1) {
      throw new CreateGoalException(ErrorCode.GOAL_NOT_CREATED, "목표시간을 설정해 주세요.");
    }

    Weight.isNullThenThrow(updateGoalRequest.getWeight());
    GoalStatus.isNullThenThrow(updateGoalRequest.getGoalStatus());

    Long goalId = updateGoalRequest.getGoalId();
    goalRepository.findById(goalId)
        .ifPresentOrElse(
            goal -> {
              if (!goal.getAccountId().equals(updateGoalRequest.getAccountId())) {
                throw new UpdateGoalException(ErrorCode.GOAL_ACCOUNT_NOT_MATCHED);
              }
            },
            () -> new EntityNotFoundException(ErrorCode.GOAL_NOT_FOUND, "존재하지 않는 목표 데이터에요. goalId: " + goalId)
        );
  }
}
