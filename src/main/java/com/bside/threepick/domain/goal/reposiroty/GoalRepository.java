package com.bside.threepick.domain.goal.reposiroty;

import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.entity.GoalStatus;
import com.bside.threepick.domain.goal.entity.GoalType;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {

  List<Goal> findGoalsByAccountIdAndGoalType(Long accountId, GoalType goalType);

  List<Goal> findGoalsByAccountIdAndGoalStatusNotIn(Long accountId, Collection<GoalStatus> goalStatuses);
}
