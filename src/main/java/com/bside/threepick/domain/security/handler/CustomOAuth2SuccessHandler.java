package com.bside.threepick.domain.security.handler;

import com.bside.threepick.domain.security.dto.Token;
import com.bside.threepick.domain.security.service.TokenService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final TokenService tokenService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    String email = ((OAuth2User) authentication.getPrincipal()).getAttribute("email");
    Token token = tokenService.generateToken(email, "ROLE_USER");
    tokenService.responseToken(response, token);
  }
}
