package com.bside.threepick.domain.retrospect.service;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.dto.request.UpdateRetrospect;
import com.bside.threepick.domain.retrospect.dto.response.RetrospectResponse;
import com.bside.threepick.domain.retrospect.entity.Retrospect;
import com.bside.threepick.domain.retrospect.repository.RetrospectRepository;
import com.bside.threepick.exception.EntityAlreadyExistsException;
import com.bside.threepick.exception.EntityNotFoundException;
import com.bside.threepick.exception.IllegalRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RetrospectService {
    private final RetrospectRepository retrospectRepository;
    private final AccountService accountService;

    public RetrospectResponse getRetrospectByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate) {
        return RetrospectResponse.fromEntity(retrospectRepository.getRetrospectByAccountIdAndRetrospectDate(accountId, retrospectDate)
                .orElseThrow(() -> new EntityNotFoundException("회고가 존재하지 않습니다. accountId : " + accountId)));
    }

    @Transactional
    public void createRetrospect(CreateRetrospectRequest createRetrospectRequest) {
        retrospectRepository.getRetrospectByAccountIdAndRetrospectDate(createRetrospectRequest.getAccountId(), createRetrospectRequest.getRetrospectDate())
                .ifPresent(account -> {
                    throw new EntityAlreadyExistsException(ErrorCode.RETROSPECT_NOT_FOUND.getCode(), "회고가 이미 존재합니다. accountId : " + createRetrospectRequest.getAccountId());
                });
        retrospectRepository.save(
                Retrospect.builder()
                        .accountId(createRetrospectRequest.getAccountId())
                        .content(createRetrospectRequest.getContent())
                        .retrospectDate(createRetrospectRequest.getRetrospectDate())
                        .build());
    }

    @Transactional
    public RetrospectResponse updateRetrospect(Long retrospectId, UpdateRetrospect updateRetrospect) {
        Retrospect retrospect = retrospectRepository.getRetrospectById(retrospectId)
                .orElseThrow(() -> new EntityNotFoundException("회고가 존재하지 않습니다. retrospectId : " + retrospectId));
        if (!retrospect.getAccountId().equals(updateRetrospect.getAccountId()))
            throw new IllegalRequestException("회고의 accountId 와 불일치합니다.");
    }
}
