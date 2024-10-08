package org.sopt.seminar1;

import javax.swing.*;
import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void postDiary(final String body) {
        Diary newDiary = new Diary(null, body);
        diaryRepository.save(newDiary);
    }

    List<Diary> getAllDiary() {
        return diaryRepository.getAllDiary();
    }
}
