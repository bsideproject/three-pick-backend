package com.bside.threepick.domain.goal.reposiroty;

import static com.bside.threepick.domain.goal.entity.QGoal.goal;

import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.entity.GoalStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoalRepositoryImpl implements GoalRepositoryQueryDsl {

  private final JPAQueryFactory queryFactory;

  public List<Goal> findGoalsDayWithoutDeleted(Long accountId, LocalDate date) {
    return findGoalsWithoutDeleted(accountId, date, null);
  }

  public List<Goal> findGoalsMonthWithoutDeleted(Long accountId, YearMonth yearMonth) {
    return findGoalsWithoutDeleted(accountId, null, yearMonth);
  }

  private List<Goal> findGoalsWithoutDeleted(Long accountId, LocalDate date, YearMonth yearMonth) {
    return queryFactory.selectFrom(goal)
        .where(
            accountIdEq(accountId),
            goalDateEq(date),
            goalDateGoeAndLt(yearMonth),
            goalStatusIn(GoalStatus.DOING, GoalStatus.DONE)
        ).fetch();
  }

  private BooleanExpression goalStatusIn(GoalStatus... goalStatuses) {
    return goalStatuses != null ? goal.goalStatus.in(goalStatuses) : null;
  }

  private BooleanExpression goalDateEq(LocalDate date) {
    return date != null ? goal.goalDate.eq(date) : null;
  }

  private BooleanExpression accountIdEq(Long accountId) {
    return accountId != null ? goal.accountId.eq(accountId) : null;
  }

  private BooleanExpression goalDateGoeAndLt(YearMonth yearMonth) {
    if (yearMonth == null) {
      return null;
    }

    LocalDate startDate = yearMonth.atDay(1);
    LocalDate endDate = yearMonth.plusMonths(1L).atDay(1);
    return goal.goalDate.goe(startDate).and(goal.goalDate.lt(endDate));
  }
}
