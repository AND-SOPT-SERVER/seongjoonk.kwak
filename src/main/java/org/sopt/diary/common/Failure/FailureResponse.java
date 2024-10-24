package org.sopt.diary.common.Failure;

public record FailureResponse(
        int status,
        String message
) {
    public static FailureResponse of(FailureCode failureCode) {
        return new FailureResponse(failureCode.getStatus().value(), failureCode.getMessage());
    }
}
