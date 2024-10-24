package org.sopt.diary.common.Failure;

import org.springframework.http.HttpStatus;

public interface FailureCode {
    String name();
    HttpStatus getStatus();
    String getMessage();
}
