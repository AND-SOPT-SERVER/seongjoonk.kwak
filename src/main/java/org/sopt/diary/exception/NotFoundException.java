package org.sopt.diary.exception;

import org.sopt.diary.common.FailureInfo;

public class NotFoundException extends BusinessException{
    public NotFoundException(final FailureInfo failureInfo) {
        super(failureInfo);
    }
}
