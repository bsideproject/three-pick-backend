package com.bside.threepick.domain.retrospect.service;

import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.service.AccountService;
import com.bside.threepick.domain.retrospect.dto.request.CreateRetrospectRequest;
import com.bside.threepick.domain.retrospect.dto.response.RetrospectResponse;
import com.bside.threepick.domain.retrospect.entity.Retrospect;
import com.bside.threepick.domain.retrospect.repository.RetrospectRepository;
import com.bside.threepick.exception.EntityAlreadyExistsException;
import com.bside.threepick.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RetrospectService {
    private final RetrospectRepository retrospectRepository;
    private final AccountService accountService;

    public RetrospectResponse getRetrospectByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate) {
        return RetrospectResponse.fromEntity(retrospectRepository.getRetrospectByAccountIdAndRetrospectDate(accountId, retrospectDate)
                .orElseThrow(() -> new EntityNotFoundException("retrospect is not found. accountId : " + accountId)));
    }

    @Transactional
    public void createRetrospect(CreateRetrospectRequest createRetrospectRequest) {
        retrospectRepository.getRetrospectByAccountIdAndRetrospectDate(createRetrospectRequest.getAccountId(), createRetrospectRequest.getRetrospectDate())
                .ifPresent(account -> {
                    throw new EntityAlreadyExistsException("retrospect is already exists. accountId : " + createRetrospectRequest.getAccountId());
                });
        retrospectRepository.save(
                Retrospect.builder()
                        .accountId(createRetrospectRequest.getAccountId())
                        .content(createRetrospectRequest.getContent())
                        .retrospectDate(createRetrospectRequest.getRetrospectDate())
                        .build());
    }
}
