package org.sopt.diary.domain.diary.api.dto.res;

import java.time.LocalDateTime;
import java.util.List;

public record DiaryListRes(
        List<DiaryIdAndTitle> diaryList
) {
    public static DiaryListRes of(final List<DiaryIdAndTitle> diaryIdAndTitle) {
        return new DiaryListRes(diaryIdAndTitle);
    }

    public record DiaryIdAndTitle(
            Long id,
            String username,
            String title,
            LocalDateTime createAt
    ) {
        public static DiaryIdAndTitle of(final Long id,
                                         final String username,
                                         final String title,
                                         final LocalDateTime createAt) {
            return new DiaryIdAndTitle(id, username, title, createAt);
        }
    }
}
