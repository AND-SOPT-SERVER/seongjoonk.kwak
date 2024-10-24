package org.sopt.diary.common.Failure;

import org.springframework.http.HttpStatus;

public enum DiaryFailureInfo implements FailureCode {

    /**
     * 400 Bad Reqeust
     */
    INVALID_CONTENT_SIZE(HttpStatus.BAD_REQUEST, "일기 내용이 30자 초과입니다."),

    /**
     * 404 Not Found
     */
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다."),
    EMPTY_DIARY(HttpStatus.NOT_FOUND, "현재 작성된 일기가 없습니다."),

    ;

    private final HttpStatus status;
    private final String message;

    DiaryFailureInfo(final HttpStatus status, final String message) {
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
