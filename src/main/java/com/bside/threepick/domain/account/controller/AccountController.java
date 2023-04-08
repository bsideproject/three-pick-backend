package com.bside.threepick.domain.account.controller;

import com.bside.threepick.domain.account.dto.AccountResponse;
import com.bside.threepick.domain.account.dto.EmailAuthResponse;
import com.bside.threepick.domain.account.dto.SignUpRequest;
import com.bside.threepick.domain.account.dto.TimeValueRequest;
import com.bside.threepick.domain.account.service.AccountService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/accounts")
@RestController
public class AccountController {

  private final AccountService accountService;

  @GetMapping("/{email}")
  public ResponseEntity<AccountResponse> findAccountByEmail(@PathVariable String email) {
    return ResponseEntity.ok(accountService.findAccountResponseByEmail(email));
  }

  @PostMapping("/{email}/auth")
  public ResponseEntity sendEmailAuthCode(@PathVariable String email) {
    accountService.sendEmailAuthCode(email);
    return ResponseEntity.ok()
        .build();
  }

  @GetMapping("/{email}/auth-check")
  public ResponseEntity<EmailAuthResponse> emailAuth(@PathVariable String email, @RequestParam String code) {
    return ResponseEntity.ok(new EmailAuthResponse(accountService.isAuthenticatedEmail(email, code)));
  }

  @PostMapping
  public ResponseEntity<AccountResponse> signup(@Validated @RequestBody SignUpRequest signUpRequest) {
    AccountResponse accountResponse = accountService.signUp(signUpRequest);
    return ResponseEntity.created(URI.create("/api/accounts/" + accountResponse.getEmail()))
        .body(accountResponse);
  }

  @PutMapping("/time-value")
  public ResponseEntity<AccountResponse> updateTimeValue(@Validated @RequestBody TimeValueRequest timeValueRequest) {
    accountService.updateTimeValue(timeValueRequest);
    return ResponseEntity.ok()
        .build();
  }
}
