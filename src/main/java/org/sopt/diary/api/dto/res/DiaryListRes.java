package org.sopt.diary.api.dto.res;

public record DiaryListRes(
        Long id,
        String title
) {
        public static DiaryListRes of(final Long id, final String title) {
            return new DiaryListRes(id, title);
        }
}
