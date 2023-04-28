package com.bside.threepick.domain.retrospect.service;

import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.dto.request.UpdateRetrospect;
import com.bside.threepick.domain.retrospect.dto.response.RetrospectResponse;
import com.bside.threepick.domain.retrospect.entity.Retrospect;
import com.bside.threepick.domain.retrospect.mapper.RetrospectMapper;
import com.bside.threepick.domain.retrospect.repository.RetrospectRepository;
import com.bside.threepick.domain.retrospect.validator.RetrospectValidator;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RetrospectService {

  private final RetrospectRepository retrospectRepository;
  private final RetrospectMapper retrospectMapper;
  private final RetrospectValidator retrospectValidator;

  @Transactional(readOnly = true)
  public RetrospectResponse findRetrospectById(Long retrospectId) {
    return RetrospectResponse.of(retrospectMapper.findById(retrospectId));
  }

  @Transactional(readOnly = true)
  public RetrospectResponse findRetrospectByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate) {
    return RetrospectResponse
        .of(retrospectMapper.findByAccountIdAndRetrospectDate(accountId, retrospectDate));
  }

  @Transactional
  public RetrospectResponse createRetrospect(CreateRetrospectRequest createRetrospectRequest) {
    Retrospect retrospect = retrospectRepository.save(retrospectMapper.createRetrospect(createRetrospectRequest));
    return RetrospectResponse.of(retrospect);
  }

  @Transactional
  public void updateRetrospect(UpdateRetrospect updateRetrospect) {
    retrospectValidator.updateRetrospect(updateRetrospect);
    retrospectRepository.findById(updateRetrospect.getRetrospectId())
        .ifPresent(retrospect -> retrospect.changeContent(updateRetrospect.getContent()));
  }
}
