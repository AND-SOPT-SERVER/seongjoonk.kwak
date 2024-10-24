package org.sopt.diary.common.util;

import org.sopt.diary.common.FailureInfo;
import org.sopt.diary.exception.BadRequestException;

public final class ValidatorUtil {
    public static void validStringLength(final String text, final int length) {
        if (text.length() > length) {
            throw new BadRequestException(FailureInfo.INVALID_CONTENT_SIZE);
        }
    }
}
