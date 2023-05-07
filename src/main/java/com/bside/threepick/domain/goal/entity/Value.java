package com.bside.threepick.domain.goal.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Value {

  @Column(name = "value", nullable = false, updatable = true)
  private BigDecimal value;

  public Value() {
    this.value = new BigDecimal(0);
  }

  public Value(int hour, int minute, Long timeValue, Weight weight) {
    this.value = new BigDecimal(weight.multiply(timeValue * (hour + ((double) minute / 60))));
  }

  public int value() {
    return value.setScale(0, RoundingMode.HALF_UP).intValue();
  }

  @Override
  public String toString() {
    return String.valueOf(value());
  }
}
