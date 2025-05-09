package org.sopt.diary.common.enums.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.apache.coyote.BadRequestException;
import org.sopt.diary.common.Failure.CommonFailureInfo;
import org.sopt.diary.exception.BusinessException;

public class ValueOfEnumValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue annotation;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    //equalsIgnoreCase -> 대소문자를 구분하지 않고 비교
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                    return true;
                }
            }
        } else {
            throw new BusinessException(CommonFailureInfo.INVALID_INPUT);
        }
        return false;
    }
}
