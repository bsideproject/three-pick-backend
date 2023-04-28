package com.bside.threepick.domain.retrospect.mapper;

import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.entity.Retrospect;
import com.bside.threepick.domain.retrospect.repository.RetrospectRepository;
import com.bside.threepick.domain.retrospect.validator.RetrospectValidator;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RetrospectMapperImpl implements RetrospectMapper {

  private final RetrospectRepository retrospectRepository;
  private final RetrospectValidator retrospectValidator;

  @Override
  public Retrospect findById(Long retrospectId) {
    retrospectValidator.findById(retrospectId);

    return retrospectRepository.findById(retrospectId)
        .get();
  }

  @Override
  public Retrospect findByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate) {
    retrospectValidator.findByAccountIdAndRetrospectDate(accountId, retrospectDate);

    return retrospectRepository.findByAccountIdAndRetrospectDate(accountId, retrospectDate)
        .get();
  }

  @Override
  public Retrospect createRetrospect(CreateRetrospectRequest createRetrospectRequest) {
    retrospectValidator.createRetrospect(createRetrospectRequest);

    return Retrospect.builder()
        .accountId(createRetrospectRequest.getAccountId())
        .content(createRetrospectRequest.getContent())
        .retrospectDate(createRetrospectRequest.getRetrospectDate())
        .build();
  }

}
