package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.service.Diary;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary() {
        diaryRepository.save(new DiaryEntity("성준"));
    }

    public List<Diary> getList() {
        // repository로부터 DiaryEntity를 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll();

        // DiaryEntity를 Diary로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for(DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getName())
            );
        }

        return diaryList;
    }
}
