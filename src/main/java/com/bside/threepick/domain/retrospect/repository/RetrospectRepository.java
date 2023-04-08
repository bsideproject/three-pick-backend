package com.bside.threepick.domain.retrospect.repository;

import com.bside.threepick.domain.retrospect.entity.Retrospect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RetrospectRepository extends JpaRepository<Retrospect, Long> {
    Optional<Retrospect> getRetrospectByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate);
}
