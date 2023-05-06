package com.bside.threepick.domain.account.mapper;

import com.bside.threepick.domain.account.dto.request.PasswordRequest;
import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.entity.SignUpType;
import com.bside.threepick.domain.account.reposiroty.AccountRepository;
import com.bside.threepick.domain.account.validator.AccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountMapperImpl implements AccountMapper {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountValidator accountValidator;

  @Override
  public Account signUp(SignUpRequest signUpRequest) {
    accountValidator.signUp(signUpRequest);

    String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
    return accountRepository.save(signUpRequest.createAccount(encodedPassword));
  }

  @Override
  public Account findById(Long accountId) {
    accountValidator.findById(accountId);

    return accountRepository.findById(accountId)
        .get();
  }

  @Override
  public Account findByEmail(String email) {
    accountValidator.findByEmail(email);

    return accountRepository.findByEmail(email)
        .get();
  }

  @Override
  public Account authenticate(String email, String password) {
    accountValidator.authenticate(email, password);

    return accountRepository.findByEmailAndSignUpType(email, SignUpType.BASIC)
        .get();
  }

  @Override
  public Account updatePassword(PasswordRequest passwordRequest) {
    accountValidator.updatePassword(passwordRequest);

    return findById(passwordRequest.getAccountId());
  }
}
