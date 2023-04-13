package com.bside.threepick.domain.retrospect.dto.request;

import lombok.Getter;

@Getter
public class UpdateRetrospect {
    private Long accountId;
    private Long retrospectId;
    private String content;
}
