package com.bside.threepick.domain.goal.mapper;

import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.dto.response.GoalDayResponse;
import com.bside.threepick.domain.goal.dto.response.GoalDayResponses;
import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.reposiroty.GoalRepository;
import com.bside.threepick.domain.goal.validator.GoalValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
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

    if (createGoalRequest.getGoalType().isToday()) {
      return goalRepository.save(createGoalRequest.createGoalToday(timeValue));
    }
    return goalRepository.save(createGoalRequest.createGoalMonth(timeValue));
  }

  @Override
  public GoalDayResponses findGoalsByAccountIdAndDate(Long accountId, LocalDate date) {
    List<GoalDayResponse> goalResponses = goalRepository.findGoalsDayWithoutDeleted(accountId, date)
        .stream()
        .map(GoalDayResponse::of)
        .collect(Collectors.toList());

    if (goalResponses.size() == 0) {
      Long timeValue = accountService.findAccountResponseById(accountId).getTimeValue();
      return new GoalDayResponses(accountId, timeValue);
    }
    return new GoalDayResponses(accountId, goalResponses);
  }
}
