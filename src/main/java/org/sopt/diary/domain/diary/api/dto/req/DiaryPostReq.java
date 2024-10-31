package org.sopt.diary.domain.diary.api.dto.req;

public record DiaryPostReq(
        String title,
        String content,
        String category,
        boolean isPrivate
        ) {

}
