package org.sopt.diary.exception;

import org.sopt.diary.common.FailureInfo;

public class IllegalArgumentException extends BusinessException{
    public IllegalArgumentException(FailureInfo failureInfo) {
        super(failureInfo);
    }
}
