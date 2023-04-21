package com.bside.threepick.domain.goal.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class GoalResponses {

  private Long accountId;
  private List<GoalResponse> goalResponses = new ArrayList<>();
}
