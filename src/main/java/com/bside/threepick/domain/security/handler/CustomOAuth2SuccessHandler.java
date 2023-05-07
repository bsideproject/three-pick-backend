package com.bside.threepick.domain.security.handler;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.account.reposiroty.AccountRepository;
import com.bside.threepick.domain.security.dto.response.Token;
import com.bside.threepick.domain.security.service.TokenService;
import com.bside.threepick.exception.EntityNotFoundException;
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
  private final AccountRepository accountRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {

    String email = ((OAuth2User) authentication.getPrincipal()).getAttribute("email");
    Long accountId = accountRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "계정이 존재하지 않아요. email: " + email))
        .getId();

    Token token = tokenService.generateToken(email, accountId, "ROLE_USER");
    tokenService.responseToken(response, token);
  }
}
