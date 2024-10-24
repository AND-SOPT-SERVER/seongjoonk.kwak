package org.sopt.diary.common.Failure;

import org.springframework.http.HttpStatus;

public enum CommonFailureInfo implements FailureCode{

    /**
     * 400 Bad Reqeust
     */
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 요청값입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    CommonFailureInfo(final HttpStatus status, final String message) {
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
