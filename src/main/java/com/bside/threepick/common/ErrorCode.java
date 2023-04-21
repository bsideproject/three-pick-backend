package com.bside.threepick.common;

public enum ErrorCode {
  // account
  ACCOUNT_NOT_FOUND(10000, "회원 정보가 없어요."),
  ACCOUNT_ALREADY_EXISTS(10001, "이미 존재하는 회원정보에요."),
  TIME_VALUE_NOT_CHANGED(10002, "한 시간의 가치를 변경할 수 있는 횟수를 모두 사용하셨어요."),

  // retrospect
  RETROSPECT_NOT_FOUND(20000, "회고가 이미 존재해요."),
  RETROSPECT_ACCOUNT_ID_DIFFERENT(20001, "회고 내 회원 ID 가 불일치해요."),

  // goal
  GOAL_NOT_FOUND(30000, "존재하지 않는 목표 데이터에요."),
  TIME_VALUE_NOT_FOUND(30001, "목표등록 전에 한시간의 가치를 먼저 등록해 주세요."),
  GOAL_NOT_CREATED(30002, "등록할 수 있는 목표의 갯수를 초과했어요."),
  GOAL_ACCOUNT_NOT_MATCHED(30003, "목표 정보와 사용자 정보가 일치하지 않아요."),

  // common
  BAD_REQUEST(40000, "잘못된 요청정보에요."),

  // token
  UNAUTHORIZED(50000, "토큰이 유효하지 않아요.");


  private int code;
  private String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
