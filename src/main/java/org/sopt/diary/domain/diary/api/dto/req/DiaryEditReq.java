package org.sopt.diary.domain.diary.api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.sopt.diary.common.enums.Category;
import org.sopt.diary.common.enums.validation.EnumValue;

public record DiaryEditReq(
        @NotBlank(message = "contetn 값이 없으면 안됩니다.")
        @Size(min = 1, max = 30, message = "일기내용은 1~30글자여야합니다.")
        String content,
        @EnumValue(enumClass = Category.class, message = "유효하지 않은 카테고리입니다", ignoreCase = false)
        @NotBlank(message = "category 값이 없으면 안됩니다.")
        Category category
) {
}
