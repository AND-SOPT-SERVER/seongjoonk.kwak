package org.sopt.diary.api.dto.res;

import java.util.List;

public record DiaryListRes(
        List<DiaryIdAndTitle> diaryList
) {
        public static DiaryListRes of(final List<DiaryIdAndTitle> diaryIdAndTitle) {
            return new DiaryListRes(diaryIdAndTitle);
        }

        public record DiaryIdAndTitle(
                Long id,
                String title
        ) {
            public static DiaryIdAndTitle of(final Long id, final String title) {
                return new DiaryIdAndTitle(id, title);
            }
        }
}
