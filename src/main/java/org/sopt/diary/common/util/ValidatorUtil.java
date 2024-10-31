package org.sopt.diary.common.util;

import org.sopt.diary.common.Failure.DiaryFailureInfo;
import org.sopt.diary.exception.BadRequestException;

import java.util.List;

public final class ValidatorUtil {
    public static void validStringLength(final String text, final int length) {
        if (text.length() > length) {
            throw new BadRequestException(DiaryFailureInfo.INVALID_CONTENT_SIZE);
        }
    }

    public static boolean isListEmpty(final List<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
