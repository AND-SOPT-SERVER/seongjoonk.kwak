package org.sopt.diary.exception;

import org.sopt.diary.common.FailureInfo;

public class BadRequestException extends BusinessException{
    public BadRequestException(final FailureInfo failureInfo) {
        super(failureInfo);
    }
}
