package com.bside.threepick.domain.goal.reposiroty;

import static com.bside.threepick.domain.goal.entity.QGoal.goal;

import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.entity.GoalStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class GoalQueryDslRepository {

  private final JPAQueryFactory queryFactory;

  public List<Goal> findGoalsByAccountIdAndDate(Long accountId, LocalDate date) {
    return queryFactory.selectFrom(goal)
        .where(
            goal.accountId.eq(accountId),
            goal.goalDate.eq(date),
            goal.goalStatus.ne(GoalStatus.DELETED)
        ).fetch();
  }
}
