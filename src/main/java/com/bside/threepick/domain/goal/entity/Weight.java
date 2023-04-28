package com.bside.threepick.domain.goal.entity;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.exception.EnumBindingException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Weight {
  POINT_FIVE(0.5), ONE(1.0), TWO(2.0);

  @JsonValue
  private double value;

  Weight(double weight) {
    this.value = weight;
  }

  @JsonCreator
  public static Weight from(Double weightParam) {
    for (Weight weight : Weight.values()) {
      if (weight.equals(weightParam)) {
        return weight;
      }
    }
    return null;
  }

  public static void isNullThenThrow(Weight weightParam) {
    if (weightParam == null) {
      String errorMsg = "";
      for (Weight weight : Weight.values()) {
        errorMsg += weight.toString() + ",";
      }
      errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
      throw new EnumBindingException(ErrorCode.BAD_REQUEST, "weight[" + errorMsg + "] 중에 값이 없어요.");
    }
  }

  public double multiply(double timeValue) {
    return value * timeValue;
  }

  public boolean equals(Double weight) {
    return this.value == weight;
  }

  public double value() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
