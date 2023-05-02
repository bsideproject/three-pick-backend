package com.bside.threepick.domain.account.service;

import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.dto.request.TempPasswordRequest;
import com.bside.threepick.domain.account.dto.request.TimeValueRequest;
import com.bside.threepick.domain.account.dto.response.AccountResponse;
import com.bside.threepick.domain.account.dto.response.GoalMonthResponse;
import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.event.EmailAuthRequestedEvent;
import com.bside.threepick.domain.account.event.TempPasswordRequestedEvent;
import com.bside.threepick.domain.account.mapper.AccountMapper;
import com.bside.threepick.domain.account.reposiroty.AccountRepository;
import com.bside.threepick.domain.account.validator.AccountValidator;
import com.bside.threepick.domain.goal.entity.Goal;
import com.bside.threepick.domain.goal.reposiroty.GoalRepository;
import java.time.YearMonth;
import java.util.UUID;
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
  private final GoalRepository goalRepository;
  private final StringRedisTemplate stringRedisTemplate;
  private final ApplicationEventPublisher eventPublisher;
  private final AccountMapper accountMapper;
  private final AccountValidator accountValidator;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public AccountResponse signUp(SignUpRequest signUpRequest) {
    return AccountResponse.of(accountMapper.signUp(signUpRequest));
  }

  @Transactional(readOnly = true)
  public void sendEmailAuthCode(String email) {
    accountValidator.sendEmailAuthCode(email);
    eventPublisher.publishEvent(new EmailAuthRequestedEvent(email));
  }

  @Transactional(readOnly = true)
  public boolean isAuthenticatedEmail(String email, String code) {
    return code.equals(stringRedisTemplate.opsForValue().get(email));
  }

  @Transactional
  public AccountResponse findAccountResponseById(Long accountId) {
    Account account = accountMapper.findById(accountId);

    account.checkNextTimeValue();

    return new AccountResponse(account, createGoalMonthResponse(accountId));
  }

  @Transactional(readOnly = true)
  public AccountResponse findAccountResponseByEmail(String email) {
    Account account = accountMapper.findByEmail(email);
    return new AccountResponse(account, createGoalMonthResponse(account.getId()));
  }

  private GoalMonthResponse createGoalMonthResponse(Long accountId) {
    GoalMonthResponse goalMonthResponse = null;
    YearMonth yearMonth = YearMonth.now();
    Goal goalMonth = goalRepository.findGoalMonthWithoutDeleted(accountId, yearMonth);
    if (goalMonth != null) {
      goalMonthResponse = new GoalMonthResponse(goalMonth, yearMonth);
    }
    return goalMonthResponse;
  }


  @Transactional
  public Account authenticate(String email, String password) {
    Account account = accountMapper.authenticate(email, password);

    account.changeLastLoginDate();

    return account;
  }

  @Transactional
  public void updateTimeValue(TimeValueRequest timeValueRequest) {
    accountValidator.updateTimeValue(timeValueRequest);
    accountRepository.findById(timeValueRequest.getAccountId())
        .ifPresent(account -> {
          if (account.getTimeValue() == null) {
            account.changeTimeValue(timeValueRequest.getTimeValue());
          } else {
            account.changeNextTimeValue(timeValueRequest.getTimeValue());
          }
        });
  }

  @Transactional
  public void updateCoachMark(Long accountId) {
    accountRepository.findById(accountId)
        .ifPresent(Account::changeCoachMark);
  }

  @Transactional
  public void updateTempPassword(TempPasswordRequest tempPasswordRequest) {
    Account account = accountMapper.findByEmail(tempPasswordRequest.getEmail());
    String newPassword = makeUUID();
    String encodedPassword = passwordEncoder.encode(newPassword);
    account.changeTempPassword(encodedPassword);
    eventPublisher.publishEvent(new TempPasswordRequestedEvent(account.getEmail(), newPassword));
  }

  private String makeUUID() {
    return UUID.randomUUID()
        .toString()
        .substring(0, 6)
        .toUpperCase();
  }
}
