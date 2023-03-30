package com.bside.threepick.domain.security.service;

import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.entity.SignUpType;
import com.bside.threepick.domain.account.reposiroty.AccountRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class CustomOidcAccountService implements OAuth2UserService<OidcUserRequest, OidcUser> {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

    OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
    OidcUser oidcUser = oidcUserService.loadUser(userRequest);

    if (accountRepository.findByEmail(oidcUser.getEmail())
        .isPresent()) {
      return oidcUser;
    }
    String email = oidcUser.getAttribute("email");
    String nickname = oidcUser.getAttribute("nickname");
    String password = UUID.randomUUID()
        .toString()
        .replace("-", "")
        .substring(0, 20);
    password = passwordEncoder.encode(password);

    Account account = new Account(email, password, nickname, SignUpType.KAKAO);
    accountRepository.save(account);

    return oidcUser;
  }
}
