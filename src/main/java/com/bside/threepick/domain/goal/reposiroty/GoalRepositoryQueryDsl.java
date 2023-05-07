package com.bside.threepick.domain.goal.reposiroty;

import com.bside.threepick.domain.goal.entity.Goal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface GoalRepositoryQueryDsl {

  List<Goal> findGoalsDayWithoutDeleted(Long accountId, LocalDate date);

  List<Goal> findGoalsRewardWithoutDeleted(Long accountId, YearMonth yearMonth);

  Goal findGoalMonthWithoutDeleted(Long accountId, YearMonth yearMonth);
}
