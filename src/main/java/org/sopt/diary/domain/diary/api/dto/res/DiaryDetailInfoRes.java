package org.sopt.diary.domain.diary.api.dto.res;

public record DiaryDetailInfoRes(
        Long id,
        String title,
        String content,
        String createdDate,
        String category
) {
    public static DiaryDetailInfoRes of(
            final Long id,
            final String title,
            final String content,
            final String createdDate,
            final String category
    ) {
        return new DiaryDetailInfoRes(id, title, content, createdDate, category);
    }
}
