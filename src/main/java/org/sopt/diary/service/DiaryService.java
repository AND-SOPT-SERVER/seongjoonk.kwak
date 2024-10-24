package org.sopt.diary.service;

import org.sopt.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
        return diaryEntityList.stream()
                .sorted(Comparator.comparing(DiaryEntity::getId)) //Id를 내려줄때, 다시 오름차순으로 정렬
                .map(diaryEntity -> DiaryListRes.of(diaryEntity.getId(), diaryEntity.getTitle()))
                .toList();
    }
}
