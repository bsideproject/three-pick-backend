package com.bside.threepick.domain.security.handler;

import com.bside.threepick.domain.security.dto.Token;
import com.bside.threepick.domain.security.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    String email = ((OAuth2User) authentication.getPrincipal()).getAttribute("email");
    Token token = tokenService.generateToken(email, "ROLE_USER");

    response.addHeader("Auth", token.getAccessToken());
    response.addHeader("Refresh", token.getRefreshToken());
    response.setContentType("application/json;charset=UTF-8");

    var writer = response.getWriter();
    writer.println(objectMapper.writeValueAsString(token));
    writer.flush();
  }
}
