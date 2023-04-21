package com.bside.threepick.domain.retrospect.entity;

import com.bside.threepick.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RETROSPECT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Retrospect extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO : Account 구현완료 시 연관관계 구현
  // @ManyToOne
  // @JoinColumn(name = "account_id")
  private Long accountId;
  private String content;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate retrospectDate;

  @Builder
  public Retrospect(Long accountId, String content, LocalDate retrospectDate) {
    this.accountId = accountId;
    this.content = content;
    this.retrospectDate = retrospectDate;
  }
}
