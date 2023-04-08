package com.bside.threepick.domain.retrospect.dto.response;

import com.bside.threepick.domain.retrospect.entity.Retrospect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RetrospectResponse {
    private Long id;
    private Long accountId;
    private String content;
    private LocalDate retrospectDate;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public static RetrospectResponse fromEntity(Retrospect retrospect) {
        return RetrospectResponse.builder()
                .accountId(retrospect.getAccountId())
                .id(retrospect.getId())
                .content(retrospect.getContent())
                .retrospectDate(retrospect.getRetrospectDate())
                .createdDate(retrospect.getCreatedDate())
                .modifiedDate(retrospect.getModifiedDate())
                .build();
    }
}
