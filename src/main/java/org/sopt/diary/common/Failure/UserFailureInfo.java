package org.sopt.diary.common.Failure;


import org.springframework.http.HttpStatus;

public enum UserFailureInfo implements FailureCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 유저입니다"),
    INVALID_USER_PASSWROD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),

    ;

    private final HttpStatus status;
    private final String message;

    UserFailureInfo(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}