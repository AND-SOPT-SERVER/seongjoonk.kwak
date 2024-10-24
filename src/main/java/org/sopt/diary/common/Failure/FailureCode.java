package org.sopt.diary.common.Failure;

import org.springframework.http.HttpStatus;

public interface FailureCode {
    HttpStatus getStatus();
    String getMessage();
}
