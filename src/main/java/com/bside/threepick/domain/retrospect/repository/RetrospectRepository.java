package com.bside.threepick.domain.retrospect.repository;

import com.bside.threepick.domain.retrospect.entity.Retrospect;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetrospectRepository extends JpaRepository<Retrospect, Long> {

  Optional<Retrospect> getRetrospectByAccountIdAndRetrospectDate(Long accountId, LocalDate retrospectDate);

  Optional<Retrospect> getRetrospectById(Long id);
}
