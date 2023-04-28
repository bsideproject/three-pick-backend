package com.bside.threepick.domain.retrospect.controller;

import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.dto.request.UpdateRetrospect;
import com.bside.threepick.domain.retrospect.dto.response.RetrospectResponse;
import com.bside.threepick.domain.retrospect.service.RetrospectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/retrospects")
@RequiredArgsConstructor
public class RetrospectController {

  private final RetrospectService retrospectService;

  @ApiOperation("회고 조회")
  @ApiImplicitParam(name = "date", value = "yyyy-MM-dd")
  @GetMapping
  public ResponseEntity<RetrospectResponse> getRetrospect(@RequestParam(name = "account-id") Long accountId,
      @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    return ResponseEntity.ok(retrospectService.getRetrospectByAccountIdAndRetrospectDate(accountId, date));
  }

  @ApiOperation("회고 등록")
  @PostMapping
  public ResponseEntity<RetrospectResponse> createRetrospect(
      @RequestBody CreateRetrospectRequest createRetrospectRequest) {
    retrospectService.createRetrospect(createRetrospectRequest);
    return ResponseEntity.ok(retrospectService.getRetrospectByAccountIdAndRetrospectDate(
        createRetrospectRequest.getAccountId(), createRetrospectRequest.getRetrospectDate()));
  }

  @ApiOperation("회고 수정")
  @PutMapping
  public ResponseEntity<RetrospectResponse> updateRetrospect(@RequestBody UpdateRetrospect updateRetrospect) {
    return ResponseEntity.ok(retrospectService.updateRetrospect(updateRetrospect));
  }
}
