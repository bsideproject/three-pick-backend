package com.bside.threepick.domain.account.mapper;

import com.bside.threepick.domain.account.dto.request.PasswordRequest;
import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.entity.Account;

public interface AccountMapper {

  Account signUp(SignUpRequest signUpRequest);

  Account findById(Long accountId);

  Account findByEmail(String email);

  Account authenticate(String email, String password);

  Account updatePassword(PasswordRequest passwordRequest);
}
