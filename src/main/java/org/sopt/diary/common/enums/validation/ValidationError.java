package org.sopt.diary.common.enums.validation;

import org.sopt.diary.common.GlobalExceptionHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public record ValidationError(
        String message) {

    public static List<ValidationError> of(final BindingResult bindingResult){
        return bindingResult.getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getDefaultMessage()))
                .toList();
    }
}
