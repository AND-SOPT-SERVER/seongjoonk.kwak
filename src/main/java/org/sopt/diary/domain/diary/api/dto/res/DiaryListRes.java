package org.sopt.diary.domain.diary.api.dto.res;

import java.time.LocalDateTime;
import java.util.List;

public record DiaryListRes(
        List<DiaryInfo> diaryList
) {
    public static DiaryListRes of(final List<DiaryInfo> diaryInfo) {
        return new DiaryListRes(diaryInfo);
    }

    public record DiaryInfo(
            Long id,
            String username,
            String title,
            LocalDateTime createAt
    ) {
        public static DiaryInfo of(final Long id,
                                   final String username,
                                   final String title,
                                   final LocalDateTime createAt) {
            return new DiaryInfo(id, username, title, createAt);
        }
    }
}
