package com.bside.threepick.domain.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Token {

  private String accessToken;
  private String refreshToken;
}

