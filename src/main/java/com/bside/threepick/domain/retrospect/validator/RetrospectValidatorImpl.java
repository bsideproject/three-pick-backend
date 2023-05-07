package com.bside.threepick.domain.retrospect.validator;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.dto.request.UpdateRetrospect;
import com.bside.threepick.domain.retrospect.entity.Retrospect;
import com.bside.threepick.domain.retrospect.repository.RetrospectRepository;
import com.bside.threepick.exception.EntityAlreadyExistsException;
import com.bside.threepick.exception.EntityNotFoundException;
import com.bside.threepick.exception.IllegalRequestException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RetrospectValidatorImpl implements RetrospectValidator {

  private final RetrospectRepository retrospectRepository;

  @Override
  public void findById(Long retrospectId) {
    retrospectRepository.findById(retrospectId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.RETROSPECT_NOT_FOUND,
            "회고가 존재하지 않아요. retrospectId : " + retrospectId));
  }

  @Override
  public void findByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate) {
    retrospectRepository.findByAccountIdAndRetrospectDate(accountId, retrospectDate)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.RETROSPECT_NOT_FOUND,
            "회고가 존재하지 않아요. accountId : " + accountId));
  }

  @Override
  public void createRetrospect(CreateRetrospectRequest createRetrospectRequest) {
    retrospectRepository.findByAccountIdAndRetrospectDate(createRetrospectRequest.getAccountId(),
        createRetrospectRequest.getRetrospectDate())
        .ifPresent(account -> {
          throw new EntityAlreadyExistsException(ErrorCode.RETROSPECT_NOT_FOUND,
              "회고가 이미 존재해요. accountId : " + createRetrospectRequest.getAccountId());
        });
  }

  @Override
  public void updateRetrospect(UpdateRetrospect updateRetrospect) {
    Long retrospectId = updateRetrospect.getRetrospectId();
    Retrospect retrospect = retrospectRepository.findById(retrospectId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.RETROSPECT_NOT_FOUND,
            "회고가 존재하지 않아요. retrospectId : " + retrospectId));
    if (!retrospect.getAccountId().equals(updateRetrospect.getAccountId())) {
      throw new IllegalRequestException(ErrorCode.RETROSPECT_ACCOUNT_ID_DIFFERENT, "회고의 accountId 와 불일치해요.");
    }
  }
}
