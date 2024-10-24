package org.sopt.diary.service;

import org.sopt.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(final DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(final DiaryPostReq diaryPostReq) {
        final DiaryEntity newDiaryEntity = DiaryEntity.create(diaryPostReq.title(), diaryPostReq.content());
        diaryRepository.save(newDiaryEntity);
    }

    public List<DiaryListRes> getDiaryList() {
        List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByIdDesc();
        diaryEntityList.stream().
    }

}
