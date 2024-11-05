package org.sopt.diary.common.Failure;

import org.springframework.http.HttpStatus;

public enum CommonFailureInfo implements FailureCode{

    /**
     * 400 Bad Reqeust
     */
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 요청값입니다."),
    MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "필요한 헤더값이 없습니다."),
    INVALID_HEADER_TYPE(HttpStatus.BAD_REQUEST, "의 타입이 잘못되었습니다."),
    MISSING_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "파라미터값이 없습니다."),
    INVALID_END_POINT(HttpStatus.BAD_REQUEST, "잘못된 엔드포인트 접근입니다."),
    ALREADY_EXITST_TITLE(HttpStatus.CONFLICT, "이미 존재하는 제목입니다."),


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
