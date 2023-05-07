package com.bside.threepick.domain.retrospect.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateRetrospect {

  @NotNull
  private Long accountId;

  @NotNull
  private Long retrospectId;

  @NotBlank
  private String content;
}
