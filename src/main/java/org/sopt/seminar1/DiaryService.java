package org.sopt.seminar1;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void postDiary(final String body) {
        diaryRepository.save(body);
    }

    List<Diary> getAllDiary() {
        List<Diary> diaryList = diaryRepository.getAllDiary();
        diaryList.stream()
                .filter(Diary::isDeleted)
                .forEach(diary -> diary.setBody("삭제된 일기입니다."));
        return diaryList;
    }

    void deleteDiary(final Long id) {
        diaryRepository.delete(id);
    }
}
