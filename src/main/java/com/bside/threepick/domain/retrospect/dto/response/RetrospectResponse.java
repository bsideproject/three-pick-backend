package com.bside.threepick.domain.retrospect.dto.response;

import com.bside.threepick.domain.retrospect.entity.Retrospect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RetrospectResponse {

  private Long retrospectId;
  private Long accountId;
  private String content;
  private LocalDate retrospectDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime modifiedDate;

  public static RetrospectResponse of(Retrospect retrospect) {
    return RetrospectResponse.builder()
        .accountId(retrospect.getAccountId())
        .retrospectId(retrospect.getId())
        .content(retrospect.getContent())
        .retrospectDate(retrospect.getRetrospectDate())
        .createdDate(retrospect.getCreatedDate())
        .modifiedDate(retrospect.getModifiedDate())
        .build();
  }
}
