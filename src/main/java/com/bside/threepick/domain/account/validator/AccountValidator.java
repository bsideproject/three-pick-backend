package com.bside.threepick.domain.account.validator;

import com.bside.threepick.domain.account.dto.request.PasswordRequest;
import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.dto.request.TimeValueRequest;

public interface AccountValidator {

  void signUp(SignUpRequest signUpRequest);

  void sendEmailAuthCode(String email);

  void findById(Long accountId);

  void authenticate(String email, String password);

  void passwordMatches(String password, String accountPassword);

  void updateTimeValue(TimeValueRequest timeValueRequest);

  void findByEmail(String email);

  void updatePassword(PasswordRequest passwordRequest);
}
