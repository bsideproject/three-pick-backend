package com.bside.threepick.domain.account.controller;

import com.bside.threepick.domain.account.dto.request.NickNameRequest;
import com.bside.threepick.domain.account.dto.request.PasswordRequest;
import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.dto.request.TempPasswordRequest;
import com.bside.threepick.domain.account.dto.request.TimeValueRequest;
import com.bside.threepick.domain.account.dto.response.AccountResponse;
import com.bside.threepick.domain.account.dto.response.EmailAuthResponse;
import com.bside.threepick.domain.account.dto.response.SignUpResponse;
import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.security.dto.response.Token;
import com.bside.threepick.domain.security.service.TokenService;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

  @ApiOperation("사용자 정보 조회(accountId)")
  @GetMapping("/{accountId}")
  public ResponseEntity<AccountResponse> findAccountById(@PathVariable Long accountId) {
    return ResponseEntity.ok(accountService.findAccountResponseById(accountId));
  }

  @ApiOperation("사용자 정보 조회(email)")
  @GetMapping("/{email}/email")
  public ResponseEntity<AccountResponse> findAccountByEmail(
      @PathVariable @Email(message = "이메일을 다시한번 확인해주세요.") String email) {
    return ResponseEntity.ok(accountService.findAccountResponseByEmail(email));
  }

  @ApiOperation("임시 비밀번호 발급")
  @PostMapping("/temp-password")
  public ResponseEntity tempPassword(@Validated @RequestBody TempPasswordRequest tempPasswordRequest) {
    accountService.updateTempPassword(tempPasswordRequest);
    return ResponseEntity.ok()
        .build();
  }

  @ApiOperation("이메일 인증코드 발송")
  @PostMapping("/{email}/auth")
  public ResponseEntity sendEmailAuthCode(@PathVariable @Email(message = "이메일을 다시한번 확인해주세요.") String email) {
    accountService.sendEmailAuthCode(email);
    return ResponseEntity.ok()
        .build();
  }

  @ApiOperation("이메일 인증코드 검증")
  @GetMapping("/{email}/auth-check")
  public ResponseEntity<EmailAuthResponse> emailAuth(@PathVariable @Email(message = "이메일을 다시한번 확인해주세요.") String email,
      @RequestParam String code) {
    return ResponseEntity.ok(new EmailAuthResponse(accountService.isAuthenticatedEmail(email, code)));
  }

  @ApiOperation("회원가입")
  @PostMapping
  public ResponseEntity<SignUpResponse> signup(@Validated @RequestBody SignUpRequest signUpRequest) {
    AccountResponse accountResponse = accountService.signUp(signUpRequest);

    Token token = tokenService.generateToken(accountResponse.getEmail(), accountResponse.getAccountId(), "ROLE_USER");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new SignUpResponse(tokenService.makeLocation(token)));
  }

  @ApiOperation("한 시간의 가치 수정")
  @PutMapping("/time-value")
  public ResponseEntity<AccountResponse> updateTimeValue(@Validated @RequestBody TimeValueRequest timeValueRequest) {
    accountService.updateTimeValue(timeValueRequest);
    return ResponseEntity.ok(accountService.findAccountResponseById(timeValueRequest.getAccountId()));
  }

  @ApiOperation("코치마크 구분 수정")
  @PutMapping("/{accountId}/coach-mark")
  public ResponseEntity<AccountResponse> updateCoachMark(@PathVariable Long accountId) {
    accountService.updateCoachMark(accountId);
    return ResponseEntity.ok(accountService.findAccountResponseById(accountId));
  }

  @ApiOperation("닉네임 수정")
  @PutMapping("/nickname")
  public ResponseEntity<AccountResponse> updateNickname(@Validated @RequestBody NickNameRequest nickNameRequest) {
    accountService.updateNickname(nickNameRequest); // TODO: 비속어 필터링
    return ResponseEntity.ok(accountService.findAccountResponseById(nickNameRequest.getAccountId()));
  }

  @ApiOperation("비밀번호 수정")
  @PutMapping("/password")
  public ResponseEntity<AccountResponse> updatePassword(@Validated @RequestBody PasswordRequest passwordRequest) {
    accountService.updatePassword(passwordRequest);
    return ResponseEntity.ok(accountService.findAccountResponseById(passwordRequest.getAccountId()));
  }

  @ApiOperation("회원탈퇴")
  @PutMapping("{accountId}/delete")
  public ResponseEntity deleteAccount(@PathVariable Long accountId) {
    accountService.deleteAccount(accountId);
    return ResponseEntity.ok()
        .build();
  }
}
