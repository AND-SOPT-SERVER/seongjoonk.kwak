package org.sopt.diary.common.enums.validation;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.stream.Collectors;

public class Test {

    //@Size, @Notblank
    //메세지들도 가져옴
    public static String getDefaultFromHandlerMethodValidationException(HandlerMethodValidationException e) {
        return e.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return String.format("[%s: %s]",
                                fieldError.getField(),
                                fieldError.getDefaultMessage());
                    } else {
                        return String.format("[%s]",
                                error.getDefaultMessage());
                    }
                })
                .collect(Collectors.joining(" "));
    }


}
