package com.bside.threepick.domain.retrospect.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "RETROSPECT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Retrospect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO : Account 구현완료 시 연관관계 구현
    //    @ManyToOne
//    @JoinColumn(name = "account_id")
    private Long accountId;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate retrospectDate;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    @Builder
    public Retrospect(Long accountId, String content, LocalDate retrospectDate) {
        this.accountId = accountId;
        this.content = content;
        this.retrospectDate = retrospectDate;
        this.createdDate=LocalDate.now();
        this.modifiedDate=LocalDate.now();
    }
}
