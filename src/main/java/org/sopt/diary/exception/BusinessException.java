package org.sopt.diary.exception;

import org.sopt.diary.common.FailureInfo;
import org.sopt.diary.common.FailureResponse;

public class BusinessException extends RuntimeException {
    private FailureInfo failureInfo;

    public BusinessException(FailureInfo failureInfo) {
        super(failureInfo.getMessage());
        this.failureInfo = failureInfo;
    }

    public FailureInfo getFailureInfo() {
        return failureInfo;
    }
}

