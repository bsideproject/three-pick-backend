package com.bside.threepick.domain.retrospect.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateRetrospectRequest {

  @NotNull
  private Long accountId;

  @NotBlank
  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  @NotNull
  private LocalDate retrospectDate = LocalDate.now();
}
