package com.bside.threepick.common;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedDate
  @Column(name = "created_date", nullable = false, columnDefinition = "datetime default current_timestamp")
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "modified_date", nullable = false, columnDefinition = "datetime default current_timestamp")
  private LocalDateTime modifiedDate;

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }
}
