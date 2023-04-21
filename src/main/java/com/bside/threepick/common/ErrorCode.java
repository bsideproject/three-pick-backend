package com.bside.threepick.common;

public enum ErrorCode {
    // account
    ACCOUNT_NOT_FOUND(10000,"회원 정보가 없습니다."),

    // retrospect
    RETROSPECT_NOT_FOUND(20000,"회고가 이미 존재합니다."),
    RETROSPECT_ACCOUNT_ID_DIFFERENT(20001,"회고 내 회원 ID 가 불일치합니다.");

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
