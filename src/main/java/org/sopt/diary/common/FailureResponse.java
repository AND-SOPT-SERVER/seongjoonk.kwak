package org.sopt.diary.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

public class FailureResponse {
    private int status;
    private String message;

    public FailureResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static FailureResponse of(FailureInfo failureInfo) {
        return new FailureResponse(failureInfo.getStatus().value(), failureInfo.getMessage());
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
