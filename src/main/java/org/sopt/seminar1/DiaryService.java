package org.sopt.seminar1;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void postDiary(final String body) {
        diaryRepository.save(body);
    }

    List<Diary> getAllDiary() {
        return diaryRepository.getAllDiary();
    }

    void deleteDiary(final Long id) {
        diaryRepository.delete(id);
    }
}
