package org.sopt.diary.api.dto.res;

import java.util.List;

public record DiaryListRes(
        List<DiaryIdAndTitleRes> diaryEntityList) {

    public static DiaryListRes of(final List<DiaryIdAndTitleRes> diaryIdAndTitleResList) {
        return new DiaryListRes(diaryIdAndTitleResList);
    }

    public record DiaryIdAndTitleRes(
            Long id, String title
    ) {
        public static DiaryIdAndTitleRes of(final Long diaryId, final String title) {
            return new DiaryIdAndTitleRes(diaryId, title);
        }
    }
}
