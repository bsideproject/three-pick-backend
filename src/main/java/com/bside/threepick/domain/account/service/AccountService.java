package com.bside.threepick.domain.account.service;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.dto.request.TimeValueRequest;
import com.bside.threepick.domain.account.dto.response.AccountResponse;
import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.entity.SignUpType;
import com.bside.threepick.domain.account.event.EmailAuthRequestedEvent;
import com.bside.threepick.domain.account.reposiroty.AccountRepository;
import com.bside.threepick.exception.AlreadyExistsEmailException;
import com.bside.threepick.exception.ChangeTimeValueCountException;
import com.bside.threepick.exception.EntityNotFoundException;
import com.bside.threepick.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final StringRedisTemplate stringRedisTemplate;
  private final ApplicationEventPublisher eventPublisher;

  public AccountResponse signUp(SignUpRequest signUpRequest) {
    accountRepository.findByEmail(signUpRequest.getEmail()).ifPresent(account -> {
      if (account.isBasicOfSignUpType()) {
        throw new AlreadyExistsEmailException(ErrorCode.ACCOUNT_ALREADY_EXISTS,
            "이미 가입 된 이메일이에요. '이메일 로그인' 으로 로그인 해주세요! email: " + signUpRequest.getEmail());
      } else {
        throw new AlreadyExistsEmailException(ErrorCode.ACCOUNT_ALREADY_EXISTS,
            "이미 가입 된 이메일이에요. '카카오로 로그인하기' 로 로그인 해주세요! email: " + signUpRequest.getEmail());
      }
    });

    String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
    Account account = accountRepository.save(signUpRequest.createAccount(encodedPassword));
    return AccountResponse.of(account);
  }

  @Transactional(readOnly = true)
  public void sendEmailAuthCode(String email) {
    if (accountRepository.findByEmail(email)
        .isPresent()) {
      throw new AlreadyExistsEmailException(ErrorCode.ACCOUNT_ALREADY_EXISTS, "이미 가입 된 이메일이에요. '이메일 로그인' 으로 로그인 해주세요!");
    }
    eventPublisher.publishEvent(new EmailAuthRequestedEvent(email));
  }

  @Transactional(readOnly = true)
  public boolean isAuthenticatedEmail(String email, String code) {
    return code.equals(stringRedisTemplate.opsForValue().get(email));
  }

  public AccountResponse findAccountResponseById(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "계정이 존재하지 않아요. accountId: " + accountId));

    account.checkNextTimeValue();

    return AccountResponse.of(account);
  }

  public Account authenticate(String email, String password) {
    Account account = accountRepository.findByEmailAndSignUpType(email, SignUpType.BASIC)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "계정이 존재하지 않아요. email: " + email));
    if (!passwordEncoder.matches(password, account.getPassword())) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED, "비밀번호를 확인해주세요.");
    }
    account.changeLastLoginDate();
    return account;
  }

  public void updateTimeValue(TimeValueRequest timeValueRequest) {
    accountRepository.findById(timeValueRequest.getAccountId())
        .ifPresent(account -> {
          if (account.getChangeCount() > 1) {
            throw new ChangeTimeValueCountException(ErrorCode.TIME_VALUE_NOT_CHANGED);
          }
          if (account.getTimeValue() == null) {
            account.changeTimeValue(timeValueRequest.getTimeValue());
          } else {
            account.changeNextTimeValue(timeValueRequest.getTimeValue());
          }
        });
  }

  public void updateCoachMark(Long accountId) {
    accountRepository.findById(accountId)
        .ifPresent(Account::changeCoachMark);
  }
}
