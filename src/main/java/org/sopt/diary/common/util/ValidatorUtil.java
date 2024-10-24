package org.sopt.diary.common.util;

public final class ValidatorUtil {
    public static void validStringLength(final String text, final int length) {
        if (text.length() > length) {
            throw new IllegalArgumentException(text + " is too long");
        }
    }
}
