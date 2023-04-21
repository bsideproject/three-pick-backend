package com.bside.threepick.domain.goal.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TotalValue {

  @Column(name = "total_value", nullable = false, updatable = true)
  private BigDecimal value;

  public TotalValue() {
  }

  public TotalValue(int hour, int minute, Long timeValue, Weight weight) {
    this.value = new BigDecimal(weight.multiply(timeValue * (hour + (double) (minute / 60))));
  }

  public BigDecimal getValue() {
    return value;
  }
}
