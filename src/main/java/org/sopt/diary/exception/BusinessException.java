package org.sopt.diary.exception;

import org.sopt.diary.common.Failure.FailureCode;

public class BusinessException extends RuntimeException {
    private FailureCode failureCode;

    public BusinessException(final FailureCode failureCode) {
        super(failureCode.getMessage());
        this.failureCode = failureCode;
    }

    public FailureCode getFailureCode() {
        return failureCode;
    }
}

