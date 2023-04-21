package com.bside.threepick.domain.goal.controller;

import com.bside.threepick.domain.goal.dto.request.CreateGoalRequest;
import com.bside.threepick.domain.goal.dto.request.UpdateGoalRequest;
import com.bside.threepick.domain.goal.dto.response.GoalResponse;
import com.bside.threepick.domain.goal.dto.response.GoalResponses;
import com.bside.threepick.domain.goal.service.GoalService;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/goals")
@RestController
public class GoalController {

  private final GoalService goalService;

  @ApiOperation(value = "목표 조회")
  @GetMapping("/{accountId}")
  public ResponseEntity<GoalResponses> findGoalByAccountId(@PathVariable Long accountId,
      @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    return ResponseEntity.ok(goalService.findGoalsByAccountIdAndDate(accountId, date));
  }

  @ApiOperation(value = "목표 등록")
  @PostMapping
  public ResponseEntity<GoalResponse> createGoal(@Validated @RequestBody CreateGoalRequest createGoalRequest) {
    return ResponseEntity.ok(goalService.createGoal(createGoalRequest));
  }

  @ApiOperation(value = "목표 수정")
  @PutMapping
  public ResponseEntity<GoalResponse> updateGoal(@Validated @RequestBody UpdateGoalRequest updateGoalRequest) {
    goalService.updateGoal(updateGoalRequest);
    return ResponseEntity.ok(goalService.findGoalById(updateGoalRequest.getGoalId()));
  }
}
