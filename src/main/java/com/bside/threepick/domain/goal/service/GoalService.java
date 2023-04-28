package com.bside.threepick.domain.goal.service;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.dto.request.UpdateGoalRequest;
import com.bside.threepick.domain.goal.dto.response.GoalDayResponse;
import com.bside.threepick.domain.goal.dto.response.GoalDayResponses;
import com.bside.threepick.domain.goal.dto.response.GoalYearMonthResponse;
import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.entity.GoalStatus;
import com.bside.threepick.domain.goal.entity.GoalType;
import com.bside.threepick.domain.goal.entity.Weight;
import com.bside.threepick.domain.goal.reposiroty.GoalRepository;
import com.bside.threepick.exception.CreateGoalException;
import com.bside.threepick.exception.EntityNotFoundException;
import com.bside.threepick.exception.UpdateGoalException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class GoalService {

  private final AccountService accountService;
  private final GoalRepository goalRepository;

  @Transactional(readOnly = true)
  public GoalDayResponse findGoalById(Long goalId) {
    Goal goal = goalRepository.findById(goalId)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.GOAL_NOT_FOUND, "존재하지 않는 목표 데이터에요. goalId: " + goalId));
    return GoalDayResponse.of(goal);
  }

  public GoalDayResponse createGoal(CreateGoalRequest createGoalRequest) {
    Long timeValue = accountService.findAccountResponseById(createGoalRequest.getAccountId())
        .getTimeValue();
    if (timeValue == null) {
      throw new CreateGoalException(ErrorCode.TIME_VALUE_NOT_FOUND);
    }

    int limitSize = GoalType.TODAY == createGoalRequest.getGoalType() ? 2 : 0;
    if (goalRepository
        .findGoalsByAccountIdAndGoalType(createGoalRequest.getAccountId(), createGoalRequest.getGoalType())
        .size() > limitSize) {
      throw new CreateGoalException(ErrorCode.GOAL_NOT_CREATED);
    }

    if (1 > createGoalRequest.getHour() && createGoalRequest.getMinute() < 1) {
      throw new CreateGoalException(ErrorCode.GOAL_NOT_CREATED, "목표시간을 설정해 주세요.");
    }

    Weight.isNullThenThrow(createGoalRequest.getWeight());
    GoalType.isNullThenThrow(createGoalRequest.getGoalType());

    return GoalDayResponse.of(goalRepository.save(createGoalRequest.createGoal(timeValue)));
  }

  @Transactional(readOnly = true)
  public GoalDayResponses findGoalsByAccountIdAndDate(Long accountId, LocalDate date) {
    List<GoalDayResponse> goalResponses = goalRepository.findGoalsDayWithoutDeleted(accountId, date)
        .stream()
        .map(GoalDayResponse::of)
        .collect(Collectors.toList());

    return new GoalDayResponses(accountId, goalResponses);
  }

  @Transactional(readOnly = true)
  public GoalYearMonthResponse findGoalsByAccountIdAndYearMonth(Long accountId, YearMonth yearMonth) {
    List<GoalDayResponse> goalResponses = goalRepository.findGoalsMonthWithoutDeleted(accountId, yearMonth)
        .stream()
        .map(GoalDayResponse::of)
        .collect(Collectors.toList());

    return new GoalYearMonthResponse(goalResponses);
  }

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
              goal.changeGoal(updateGoalRequest.getContent(), updateGoalRequest.getGoalStatus(),
                  updateGoalRequest.getHour(), updateGoalRequest.getMinute(), updateGoalRequest.getWeight());
            },
            () -> new EntityNotFoundException(ErrorCode.GOAL_NOT_FOUND, "존재하지 않는 목표 데이터에요. goalId: " + goalId)
        );
  }
}
