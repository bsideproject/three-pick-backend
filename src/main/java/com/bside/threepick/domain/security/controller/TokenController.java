package com.bside.threepick.domain.security.controller;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.security.dto.request.SignInRequest;
import com.bside.threepick.domain.security.dto.response.LogoutResponse;
import com.bside.threepick.domain.security.dto.response.Token;
import com.bside.threepick.domain.security.service.TokenService;
import com.bside.threepick.exception.UnauthorizedException;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/tokens")
@RestController
public class TokenController {

  private final TokenService tokenService;
  private final AccountService accountService;

  @ApiOperation("RefreshToken 으로 AccessToken, RefreshToken 갱신")
  @GetMapping("/refresh")
  public ResponseEntity<Token> refreshAuth(HttpServletRequest request) {
    String token = request.getHeader("Refresh");

    if (token != null && tokenService.verifyToken(token, false)) {
      String email = tokenService.getSubject(token);
      Long accountId = tokenService.getAccountId(token);
      return ResponseEntity.ok(tokenService.generateToken(email, accountId, "ROLE_USER"));
    }

    throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
  }

  @ApiOperation("로그인")
  @PostMapping("/signin")
  public ResponseEntity<Token> signin(@RequestBody SignInRequest signInRequest) {
    Account authenticatedAccount = accountService.authenticate(signInRequest.getEmail(),
        signInRequest.getPassword());
    return ResponseEntity.ok(tokenService.generateToken(authenticatedAccount.getEmail(),
        authenticatedAccount.getId(), "ROLE_USER"));
  }

  @ApiOperation("로그아웃")
  @PostMapping("/logout")
  public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
    tokenService.logout(request);
    return ResponseEntity.ok()
        .body(new LogoutResponse(true));
  }
}
