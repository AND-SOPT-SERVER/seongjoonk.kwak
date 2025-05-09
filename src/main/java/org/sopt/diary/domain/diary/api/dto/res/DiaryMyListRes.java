package org.sopt.diary.domain.diary.api.dto.res;

import java.time.LocalDateTime;
import java.util.List;

public record DiaryMyListRes(
        List<DiaryMyListRes.DiaryMyInfo> diaryList
) {
    public static DiaryMyListRes of(final List<DiaryMyListRes.DiaryMyInfo> diaryInfo) {
        return new DiaryMyListRes(diaryInfo);
    }

    public record DiaryMyInfo(
            Long id,
            String title,
            LocalDateTime createAt
    ) {
        public static DiaryMyListRes.DiaryMyInfo of(final Long id,
                                                final String title,
                                                final LocalDateTime createAt) {
            return new DiaryMyListRes.DiaryMyInfo(id, title, createAt);
        }
    }
}