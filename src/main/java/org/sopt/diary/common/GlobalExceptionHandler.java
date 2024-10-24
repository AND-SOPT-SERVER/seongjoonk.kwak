package org.sopt.diary.common;

import org.sopt.diary.common.Failure.CommonFailureInfo;
import org.sopt.diary.common.Failure.DiaryFailureInfo;
import org.sopt.diary.common.Failure.FailureResponse;
import org.sopt.diary.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<FailureResponse> handleBusinessException(final BusinessException e) {
        return ResponseEntity.status(e.getFailureCode().getStatus()).body(FailureResponse.of(e.getFailureCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<FailureResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(CommonFailureInfo.INVALID_INPUT));
    }
}
