package com.bside.threepick.domain.retrospect.entity;

import com.bside.threepick.common.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "retrospect")
public class Retrospect extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO : Account 구현완료 시 연관관계 구현
  // @ManyToOne
  // @JoinColumn(name = "account_id")
  @Column(name = "account_id", nullable = false, updatable = false)
  private Long accountId;

  @Column(name = "content", nullable = false, updatable = true)
  private String content;

  @Column(name = "retrospect_date", nullable = false, updatable = false)
  private LocalDate retrospectDate;

  protected Retrospect() {
  }

  @Builder
  public Retrospect(Long accountId, String content, LocalDate retrospectDate) {
    this.accountId = accountId;
    this.content = content;
    this.retrospectDate = retrospectDate;
  }

  public void changeContent(String content) {
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public String getContent() {
    return content;
  }

  public LocalDate getRetrospectDate() {
    return retrospectDate;
  }
}
