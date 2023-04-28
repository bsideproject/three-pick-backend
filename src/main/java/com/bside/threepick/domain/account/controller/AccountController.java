package com.bside.threepick.domain.account.controller;

import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.dto.request.TimeValueRequest;
import com.bside.threepick.domain.account.dto.response.AccountResponse;
import com.bside.threepick.domain.account.dto.response.EmailAuthResponse;
import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.security.dto.response.Token;
import com.bside.threepick.domain.security.service.TokenService;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
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

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@RestController
public class AccountController {

  private final AccountService accountService;
  private final TokenService tokenService;

  @ApiOperation(value = "사용자 정보 조회")
  @GetMapping("/{accountId}")
  public ResponseEntity<AccountResponse> findAccountById(@PathVariable Long accountId) {
    return ResponseEntity.ok(accountService.findAccountResponseById(accountId));
  }

  @ApiOperation(value = "이메일 인증코드 발송")
  @PostMapping("/{email}/auth")
  public ResponseEntity sendEmailAuthCode(@PathVariable @Email(message = "이메일을 다시한번 확인해주세요.") String email) {
    accountService.sendEmailAuthCode(email);
    return ResponseEntity.ok()
        .build();
  }

  @ApiOperation(value = "이메일 인증코드 검증")
  @GetMapping("/{email}/auth-check")
  public ResponseEntity<EmailAuthResponse> emailAuth(@PathVariable @Email(message = "이메일을 다시한번 확인해주세요.") String email,
      @RequestParam String code) {
    return ResponseEntity.ok(new EmailAuthResponse(accountService.isAuthenticatedEmail(email, code)));
  }

  @ApiOperation(value = "회원가입")
  @PostMapping
  public void signup(@Validated @RequestBody SignUpRequest signUpRequest, HttpServletResponse response) {
    AccountResponse accountResponse = accountService.signUp(signUpRequest);

    Token token = tokenService.generateToken(accountResponse.getEmail(), accountResponse.getAccountId(), "ROLE_USER");
    tokenService.responseToken(response, token);
  }

  @ApiOperation(value = "한 시간의 가치 수정")
  @PutMapping("/time-value")
  public ResponseEntity<AccountResponse> updateTimeValue(@Validated @RequestBody TimeValueRequest timeValueRequest) {
    accountService.updateTimeValue(timeValueRequest);
    return ResponseEntity.ok(accountService.findAccountResponseById(timeValueRequest.getAccountId()));
  }

  @ApiOperation(value = "코치마크 구분 수정")
  @PutMapping("/{accountId}/coach-mark")
  public ResponseEntity<AccountResponse> updateCoachMark(@PathVariable Long accountId) {
    accountService.updateCoachMark(accountId);
    return ResponseEntity.ok(accountService.findAccountResponseById(accountId));
  }
}
