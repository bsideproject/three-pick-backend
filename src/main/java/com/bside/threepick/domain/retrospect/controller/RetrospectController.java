package com.bside.threepick.domain.retrospect.controller;

import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.dto.request.UpdateRetrospect;
import com.bside.threepick.domain.retrospect.dto.response.RetrospectResponse;
import com.bside.threepick.domain.retrospect.service.RetrospectService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("retrospects")
@RequiredArgsConstructor
public class RetrospectController {
    private final RetrospectService retrospectService;

    @GetMapping
    public ResponseEntity<RetrospectResponse> getRetrospect(@RequestParam(name = "account-id") Long accountId, @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(retrospectService.getRetrospectByAccountIdAndRetrospectDate(accountId, date));
    }

    @PostMapping
    public void createRetrospect(@RequestBody CreateRetrospectRequest createRetrospectRequest) {
        retrospectService.createRetrospect(createRetrospectRequest);
    }

    @PutMapping("/{retrospectId}")
    public ResponseEntity<RetrospectResponse> updateRetrospect(@PathVariable Long retrospectId, @RequestBody UpdateRetrospect updateRetrospect) {
        return ResponseEntity.ok(retrospectService.updateRetrospect(retrospectId, updateRetrospect));
    }
}
