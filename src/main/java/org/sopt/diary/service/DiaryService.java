package org.sopt.diary.service;

import org.sopt.diary.api.dto.req.DiaryEditReq;
import org.sopt.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.api.dto.res.DiaryDetailInfoRes;
import org.sopt.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.common.util.DateFormatUtil;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;


@Component
@Transactional
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(final DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(final DiaryPostReq diaryPostReq) {
        final DiaryEntity newDiaryEntity = DiaryEntity.create(diaryPostReq.title(), diaryPostReq.content());
        diaryRepository.save(newDiaryEntity);
    }

    @Transactional(readOnly = true)
    public List<DiaryListRes> getDiaryList() {
        List<DiaryEntity> findDiaryEntityList = diaryRepository.findTop10ByOrderByIdDesc();
        return findDiaryEntityList.stream()
                .sorted(Comparator.comparing(DiaryEntity::getId)) //Id를 내려줄때, 다시 오름차순으로 정렬
                .map(diaryEntity -> DiaryListRes.of(diaryEntity.getId(), diaryEntity.getTitle()))
                .toList();
    }

    @Transactional(readOnly = true)
    public DiaryDetailInfoRes getDiaryDetailInfo(final Long id) {
        final DiaryEntity findDiary = diaryRepository.findById(id).orElse(null); //추후 예외처리 할 예정
        final String createTimeString = DateFormatUtil.format(findDiary.getCreatedAt()); //LocalDateTime -> String

        return DiaryDetailInfoRes.of(findDiary.getId(), findDiary.getTitle(), findDiary.getContent(), createTimeString);
    }

    public void editDiaryContent(final Long id, final DiaryEditReq diaryEditReq) {
        final DiaryEntity findDiary = diaryRepository.findById(id).orElse(null); //추후 예외처리 할 예정
        findDiary.setContent(diaryEditReq.content()); //null 질문 답변 이후 처리
    }
}
