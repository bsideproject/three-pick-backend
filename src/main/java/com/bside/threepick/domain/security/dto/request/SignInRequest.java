package com.bside.threepick.domain.security.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInRequest {

  @NotBlank
  private String email;

  @NotBlank
  private String password;
}
