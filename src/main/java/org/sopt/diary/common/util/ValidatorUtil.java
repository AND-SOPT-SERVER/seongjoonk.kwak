package org.sopt.diary.common.util;

import org.sopt.diary.common.Failure.DiaryFailureInfo;
import org.sopt.diary.exception.BadRequestException;

public final class ValidatorUtil {
    public static void validStringLength(final String text, final int length) {
        if (text.length() > length) {
            throw new BadRequestException(DiaryFailureInfo.INVALID_CONTENT_SIZE);
        }
    }
}
