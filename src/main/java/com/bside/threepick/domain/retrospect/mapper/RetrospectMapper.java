package com.bside.threepick.domain.retrospect.mapper;

import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.entity.Retrospect;
import java.time.LocalDate;

public interface RetrospectMapper {

  Retrospect findById(Long retrospectId);

  Retrospect findByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate);

  Retrospect createRetrospect(CreateRetrospectRequest createRetrospectRequest);
}
