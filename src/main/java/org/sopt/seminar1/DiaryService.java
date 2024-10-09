package org.sopt.seminar1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void postDiary(final String body) {
        diaryRepository.save(body);
    }

    List<Diary> getAllDiary() {
        List<Diary> diaryList = diaryRepository.getAllDiary();

        //삭제된 일기는 body 내용안보이고, 삭제되었다는 것만 알려줌
        diaryList.stream()
                .filter(Diary::isDeleted)
                .forEach(diary -> diary.setBody("삭제된 일기입니다."));
        return diaryList;
    }

    void deleteDiary(final Long id) {
        diaryRepository.delete(id);
    }

    void patchDiary(final Long id, final String body) {
        diaryRepository.patch(id, body);
    }
}
