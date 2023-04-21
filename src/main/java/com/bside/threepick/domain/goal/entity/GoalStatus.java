package com.bside.threepick.domain.goal.entity;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.exception.EnumBindingException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum GoalStatus {
  DELETED, DOING, DONE;

  @JsonCreator
  public static GoalStatus from(String statusParam) {
    for (GoalStatus goalStatus : GoalStatus.values()) {
      if (goalStatus.name().equals(statusParam)) {
        return goalStatus;
      }
    }
    return null;
  }

  public static void isNullThenThrow(GoalStatus statusParam) {
    if (statusParam == null) {
      String errorMsg = "";
      for (GoalStatus status : GoalStatus.values()) {
        errorMsg += status.name() + ",";
      }
      errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
      throw new EnumBindingException(ErrorCode.BAD_REQUEST, "goalStatus[" + errorMsg + "] 중에 값이 없어요.");
    }
  }
}
