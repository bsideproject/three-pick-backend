package com.bside.threepick.domain.goal.entity;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.exception.EnumBindingException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum GoalType {
  MONTH, TODAY;

  @JsonCreator
  public static GoalType from(String typeParam) {
    for (GoalType goalType : GoalType.values()) {
      if (goalType.name().equals(typeParam)) {
        return goalType;
      }
    }
    return null;
  }

  public static void isNullThenThrow(GoalType typeParam) {
    if (typeParam == null) {
      String errorMsg = "";
      for (GoalType goalType : GoalType.values()) {
        errorMsg += goalType.name() + ",";
      }
      errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
      throw new EnumBindingException(ErrorCode.BAD_REQUEST, "goalType[" + errorMsg + "] 중에 값이 없어요.");
    }
  }

  public boolean isToday() {
    return this == TODAY;
  }
}
