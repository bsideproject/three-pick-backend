package com.bside.threepick.domain.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {

  private Long accountId;
  private String accessToken;
  private String refreshToken;
}

