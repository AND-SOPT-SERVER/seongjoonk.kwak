package org.sopt.diary.exception;

import org.sopt.diary.common.FailureInfo;

public class NotFoundException extends BusinessException{
    public NotFoundException(FailureInfo failureInfo) {
        super(failureInfo);
    }
}
