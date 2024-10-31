package org.sopt.diary.domain.diary.api.dto.req;

import org.sopt.diary.common.Category;

public record DiaryEditReq(
        String content,
        Category category
) {
}
