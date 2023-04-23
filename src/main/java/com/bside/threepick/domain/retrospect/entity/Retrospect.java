package com.bside.threepick.domain.retrospect.entity;

import com.bside.threepick.common.BaseEntity;
import java.time.LocalDate;
import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "retrospect")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Retrospect extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO : Account 구현완료 시 연관관계 구현
  // @ManyToOne
  // @JoinColumn(name = "account_id")
  @Column(name = "account_id", nullable = false)
  private Long accountId;
  @Column(name = "content", nullable = false)
  private String content;
  @Column(name = "retrospect_date", nullable = false)
  private LocalDate retrospectDate;

  @Builder
  public Retrospect(Long accountId, String content, LocalDate retrospectDate) {
    this.accountId = accountId;
    this.content = content;
    this.retrospectDate = retrospectDate;
  }

  public void changeContent(String content) {
    this.content = content;
  }
}
