package org.sopt.diary.exception;

import org.sopt.diary.common.Failure.FailureCode;

public class NotFoundException extends BusinessException{
    public NotFoundException(final FailureCode failureCode) {
        super(failureCode);
    }
}
