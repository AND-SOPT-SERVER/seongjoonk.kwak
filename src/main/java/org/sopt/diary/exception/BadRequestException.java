package org.sopt.diary.exception;

import org.sopt.diary.common.Failure.DiaryFailureInfo;
import org.sopt.diary.common.Failure.FailureCode;

public class BadRequestException extends BusinessException{
    public BadRequestException(final FailureCode failureCode) {
        super(failureCode);
    }
}
