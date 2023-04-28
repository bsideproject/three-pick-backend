package com.bside.threepick.domain.retrospect.validator;

import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.dto.request.UpdateRetrospect;
import java.time.LocalDate;

public interface RetrospectValidator {

  void findById(Long retrospectId);

  void findByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate);

  void createRetrospect(CreateRetrospectRequest createRetrospectRequest);

  void updateRetrospect(UpdateRetrospect updateRetrospect);
}
