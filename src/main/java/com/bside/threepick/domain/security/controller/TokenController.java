package com.bside.threepick.domain.security.controller;

import com.bside.threepick.domain.security.dto.Token;
import com.bside.threepick.domain.security.service.TokenService;
import com.bside.threepick.exception.UnauthorizedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/token")
@RestController
public class TokenController {

  private final TokenService tokenService;

  @GetMapping("/expired")
  public String auth() {
    throw new UnauthorizedException("토큰이 유효하지 않습니다.");
  }

  @GetMapping("/refresh")
  public Token refreshAuth(HttpServletRequest request, HttpServletResponse response) {
    String token = request.getHeader("Refresh");

    if (token != null && tokenService.verifyToken(token)) {
      String email = tokenService.getSubject(token);
      Token newToken = tokenService.generateToken(email, "USER");

      response.addHeader("Auth", newToken.getAccessToken());
      response.addHeader("Refresh", newToken.getRefreshToken());
      response.setContentType("application/json;charset=UTF-8");

      return newToken;
    }

    throw new UnauthorizedException("토큰이 유효하지 않습니다.");
  }
}
