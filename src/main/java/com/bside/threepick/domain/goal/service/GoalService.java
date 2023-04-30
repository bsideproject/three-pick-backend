package com.bside.threepick.domain.goal.service;

import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.dto.request.UpdateGoalRequest;
import com.bside.threepick.domain.goal.dto.response.GoalDayResponse;
import com.bside.threepick.domain.goal.dto.response.GoalDayResponses;
import com.bside.threepick.domain.goal.dto.response.GoalYearMonthResponse;
import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.mapper.GoalMapper;
import com.bside.threepick.domain.goal.reposiroty.GoalRepository;
import com.bside.threepick.domain.goal.validator.GoalValidator;
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

  private final GoalRepository goalRepository;
  private final GoalMapper goalMapper;
  private final GoalValidator goalValidator;

  @Transactional(readOnly = true)
  public GoalDayResponse findGoalById(Long goalId) {
    return GoalDayResponse.of(goalMapper.findById(goalId));
  }

  @Transactional
  public GoalDayResponse createGoal(CreateGoalRequest createGoalRequest) {
    return GoalDayResponse.of(goalMapper.createGoal(createGoalRequest));
  }

  @Transactional(readOnly = true)
  public GoalDayResponses findGoalsByAccountIdAndDate(Long accountId, LocalDate date) {
    return goalMapper.findGoalsByAccountIdAndDate(accountId, date);
  }

  @Transactional(readOnly = true)
  public GoalYearMonthResponse findGoalsByAccountIdAndYearMonth(Long accountId, YearMonth yearMonth) {
    List<GoalDayResponse> goalResponses = goalRepository.findGoalsMonthWithoutDeleted(accountId, yearMonth)
        .stream()
        .map(GoalDayResponse::of)
        .collect(Collectors.toList());

    return new GoalYearMonthResponse(goalResponses);
  }

  @Transactional
  public void updateGoal(UpdateGoalRequest updateGoalRequest) {
    goalValidator.updateGoal(updateGoalRequest);

    Goal goal = goalMapper.findById(updateGoalRequest.getGoalId());
    goal.changeGoal(updateGoalRequest.getContent(), updateGoalRequest.getGoalStatus(),
        updateGoalRequest.getHour(), updateGoalRequest.getMinute(), updateGoalRequest.getWeight());
  }
}
