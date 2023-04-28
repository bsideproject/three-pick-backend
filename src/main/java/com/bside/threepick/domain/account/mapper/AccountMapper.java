package com.bside.threepick.domain.account.mapper;

import com.bside.threepick.domain.account.dto.request.SignUpRequest;
import com.bside.threepick.domain.account.entity.Account;

public interface AccountMapper {

  Account signUp(SignUpRequest signUpRequest);

  Account findById(Long accountId);

  Account authenticate(String email, String password);
}
