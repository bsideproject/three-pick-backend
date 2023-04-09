package com.bside.threepick.domain.security.controller;

import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.security.dto.request.SignInRequest;
import com.bside.threepick.domain.security.dto.response.Token;
import com.bside.threepick.domain.security.service.TokenService;
import com.bside.threepick.exception.UnauthorizedException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

  @GetMapping("/expired")
  public String auth() {
    throw new UnauthorizedException("토큰이 유효하지 않습니다.");
  }

  @GetMapping("/refresh")
  public void refreshAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String token = request.getHeader("Refresh");

    if (token != null && tokenService.verifyToken(token)) {
      String email = tokenService.getSubject(token);
      Long accountId = tokenService.getAccountId(token);
      Token newToken = tokenService.generateToken(email, accountId, "ROLE_USER");
      tokenService.responseToken(response, newToken);
    }

    throw new UnauthorizedException("토큰이 유효하지 않습니다.");
  }

  @PostMapping("/signin")
  public void signin(@RequestBody SignInRequest signInRequest, HttpServletResponse response) throws IOException {
    Account authenticatedAccount = accountService.authenticate(signInRequest.getEmail(),
        signInRequest.getPassword());
    Token token = tokenService.generateToken(authenticatedAccount.getEmail(), authenticatedAccount.getId(),
        "ROLE_USER");
    tokenService.responseToken(response, token);
  }
}
