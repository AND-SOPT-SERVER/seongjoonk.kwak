package org.sopt.diary.common;

import org.sopt.diary.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<FailureResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(e.getFailureInfo().getStatus()).body(FailureResponse.of(e.getFailureInfo()));
    }
}
