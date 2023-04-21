package com.bside.threepick.domain.retrospect.service;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.dto.request.UpdateRetrospect;
import com.bside.threepick.domain.retrospect.dto.response.RetrospectResponse;
import com.bside.threepick.domain.retrospect.entity.Retrospect;
import com.bside.threepick.domain.retrospect.repository.RetrospectRepository;
import com.bside.threepick.exception.EntityAlreadyExistsException;
import com.bside.threepick.exception.EntityNotFoundException;
import com.bside.threepick.exception.IllegalRequestException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RetrospectService {

  private final RetrospectRepository retrospectRepository;

  @Transactional(readOnly = true)
  public RetrospectResponse getRetrospectByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate) {
    return RetrospectResponse
        .of(retrospectRepository.getRetrospectByAccountIdAndRetrospectDate(accountId, retrospectDate)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.RETROSPECT_NOT_FOUND,
                "회고가 존재하지 않아요. accountId : " + accountId)));
  }

  @Transactional
  public void createRetrospect(CreateRetrospectRequest createRetrospectRequest) {
    retrospectRepository.getRetrospectByAccountIdAndRetrospectDate(createRetrospectRequest.getAccountId(),
        createRetrospectRequest.getRetrospectDate())
        .ifPresent(account -> {
          throw new EntityAlreadyExistsException(ErrorCode.RETROSPECT_NOT_FOUND,
              "회고가 이미 존재해요. accountId : " + createRetrospectRequest.getAccountId());
        });
    retrospectRepository.save(
        Retrospect.builder()
            .accountId(createRetrospectRequest.getAccountId())
            .content(createRetrospectRequest.getContent())
            .retrospectDate(createRetrospectRequest.getRetrospectDate())
            .build());
  }

  @Transactional
  public RetrospectResponse updateRetrospect(UpdateRetrospect updateRetrospect) {
    Long retrospectId = updateRetrospect.getRetrospectId();
    Retrospect retrospect = retrospectRepository.getRetrospectById(retrospectId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.RETROSPECT_NOT_FOUND,
            "회고가 존재하지 않아요. retrospectId : " + retrospectId));
    if (!retrospect.getAccountId().equals(updateRetrospect.getAccountId())) {
      throw new IllegalRequestException(ErrorCode.RETROSPECT_ACCOUNT_ID_DIFFERENT, "회고의 accountId 와 불일치해요.");
    }
    retrospect.setContent(updateRetrospect.getContent());
    return RetrospectResponse.of(retrospectRepository.save(retrospect));
  }
}
