package org.sopt.diary.service;

import org.sopt.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.api.dto.res.DiaryDetailInfoRes;
import org.sopt.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.common.util.DateFormatUtil;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
        List<DiaryEntity> findDiaryEntityList = diaryRepository.findTop10ByOrderByIdDesc();
        return findDiaryEntityList.stream()
                .sorted(Comparator.comparing(DiaryEntity::getId)) //Id를 내려줄때, 다시 오름차순으로 정렬
                .map(diaryEntity -> DiaryListRes.of(diaryEntity.getId(), diaryEntity.getTitle()))
                .toList();
    }

    public DiaryDetailInfoRes getDiaryDetailInfo(final Long id) {
        final DiaryEntity findDiary = diaryRepository.findById(id).orElse(null); //추후 예외잡기
        final String createTimeString = DateFormatUtil.format(findDiary.getCreatedAt()); //LocalDateTime -> String

        return DiaryDetailInfoRes.of(findDiary.getId(), findDiary.getTitle(), findDiary.getContent(), createTimeString);
    }
}
