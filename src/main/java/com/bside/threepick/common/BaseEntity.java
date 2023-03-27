package com.bside.threepick.common;

import java.time.Instant;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedDate
  private Instant createdDate;

  @LastModifiedDate
  private Instant modifiedDate;

  public Instant getCreatedDate() {
    return createdDate;
  }

  public Instant getModifiedDate() {
    return modifiedDate;
  }
}
