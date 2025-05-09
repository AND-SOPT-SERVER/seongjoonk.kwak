package org.sopt.diary.common.Failure;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public record FailureResponse(
        int status,
        String message
) {

    public static FailureResponse of(final FailureCode failureCode) {
        return new FailureResponse(failureCode.getStatus().value(), failureCode.getMessage());
    }

    public static FailureResponse of(final HttpStatus status, final String message) {
        return new FailureResponse(status.value(), message);
    }
}
