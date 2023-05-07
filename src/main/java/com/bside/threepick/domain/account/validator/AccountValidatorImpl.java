package com.bside.threepick.domain.account.validator;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.account.dto.request.PasswordRequest;
import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.dto.request.TimeValueRequest;
import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.entity.SignUpType;
import com.bside.threepick.domain.account.reposiroty.AccountRepository;
import com.bside.threepick.exception.AlreadyExistsEmailException;
import com.bside.threepick.exception.ChangeTimeValueCountException;
import com.bside.threepick.exception.EntityNotFoundException;
import com.bside.threepick.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountValidatorImpl implements AccountValidator {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void signUp(SignUpRequest signUpRequest) {
    accountRepository.findByEmail(signUpRequest.getEmail()).ifPresent(account -> {
      if (account.isBasicOfSignUpType()) {
        throw new AlreadyExistsEmailException(ErrorCode.ACCOUNT_ALREADY_EXISTS,
            "이미 가입 된 이메일이에요. '이메일 로그인' 으로 로그인 해주세요! email: " + signUpRequest.getEmail());
      } else {
        throw new AlreadyExistsEmailException(ErrorCode.ACCOUNT_ALREADY_EXISTS,
            "이미 가입 된 이메일이에요. '카카오로 로그인하기' 로 로그인 해주세요! email: " + signUpRequest.getEmail());
      }
    });
  }

  @Override
  public void sendEmailAuthCode(String email) {
    if (accountRepository.findByEmail(email)
        .isPresent()) {
      throw new AlreadyExistsEmailException(ErrorCode.ACCOUNT_ALREADY_EXISTS, "이미 가입 된 이메일이에요. '이메일 로그인' 으로 로그인 해주세요!");
    }
  }

  @Override
  public void findById(Long accountId) {
    accountRepository.findById(accountId)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "계정이 존재하지 않아요. accountId: " + accountId));
  }

  @Override
  public void findByEmail(String email) {
    accountRepository.findByEmail(email)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "계정이 존재하지 않아요. email: " + email));
  }

  @Override
  public void updatePassword(PasswordRequest passwordRequest) {
    findById(passwordRequest.getAccountId());
    Account account = accountRepository.findById(passwordRequest.getAccountId())
        .get();
    passwordMatches(passwordRequest.getPassword(), account.getPassword());
  }

  @Override
  public void authenticate(String email, String password) {
    Account account = accountRepository.findByEmailAndSignUpType(email, SignUpType.BASIC)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "계정이 존재하지 않아요. email: " + email));
    account.isDeletedThenThrow();
    passwordMatches(password, account.getPassword());
  }

  @Override
  public void passwordMatches(String password, String accountPassword) {
    if (!passwordEncoder.matches(password, accountPassword)) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED, "아이디 혹은 비밀번호를 확인해주세요.");
    }
  }

  @Override
  public void updateTimeValue(TimeValueRequest timeValueRequest) {
    accountRepository.findById(timeValueRequest.getAccountId())
        .ifPresent(account -> {
          if (account.getChangeCount() > 1) {
            throw new ChangeTimeValueCountException(ErrorCode.TIME_VALUE_NOT_CHANGED);
          }
        });
  }
}
