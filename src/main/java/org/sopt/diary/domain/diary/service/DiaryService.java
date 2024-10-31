package org.sopt.diary.domain.diary.service;

import org.sopt.diary.common.Category;
import org.sopt.diary.common.Failure.UserFailureInfo;
import org.sopt.diary.common.SortBy;
import org.sopt.diary.common.util.ValidatorUtil;
import org.sopt.diary.domain.diary.api.dto.req.DiaryEditReq;
import org.sopt.diary.domain.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.domain.diary.api.dto.res.DiaryDetailInfoRes;
import org.sopt.diary.domain.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.common.Failure.DiaryFailureInfo;
import org.sopt.diary.common.util.DateFormatUtil;
import org.sopt.diary.domain.users.entity.User;
import org.sopt.diary.domain.users.repository.UserRepository;
import org.sopt.diary.exception.NotFoundException;
import org.sopt.diary.domain.diary.entity.DiaryEntity;
import org.sopt.diary.domain.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Component
@Transactional(readOnly = true)
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DiaryService(DiaryRepository diaryRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createDiary(final Long userId,
                            final String title,
                            final String content,
                            final String category,
                            final boolean isPrivate) {
        final User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(UserFailureInfo.USER_NOT_FOUND)
        );
        final DiaryEntity newDiaryEntity = DiaryEntity.create(foundUser, title, content, Category.valueOf(category), isPrivate);
        diaryRepository.save(newDiaryEntity);
    }

    // 전체 다이어리 목록 조회
    public DiaryListRes getDiaryList(final String category, final String sortBy) {
        final Category categoryEnum = Category.valueOf(category.toUpperCase());
        final SortBy sortByEnum = SortBy.valueOf(sortBy.toUpperCase());

        final List<DiaryEntity> findDiaryEntityList;

        //카테고리 초기화상태 (카테고리 상관없이 전체 조회)
        if (categoryEnum == Category.ALL) {
            findDiaryEntityList = switch (sortByEnum) {
                case LATEST -> diaryRepository.findTop10ByIsPrivateFalseOrderByCreatedAtDesc(); //최신순 정렬
                case QUANTITY -> diaryRepository.findTop10ByOrderByContentLength(); //글자수순 정렬
            };
        } else { //카테고리별로 조회
            findDiaryEntityList = switch (sortByEnum) {
                case LATEST -> diaryRepository.findTop10ByCategoryAndIsPrivateFalseOrderByCreatedAtDesc(categoryEnum); //최신순 정렬
                case QUANTITY -> diaryRepository.findTop10ByCategoryAndIsPrivateFalseOrderByContentLength(categoryEnum); //글자수순 정렬
            };
        }

        //빈 List 검증
        if (ValidatorUtil.isListEmpty(findDiaryEntityList)) {
            throw new NotFoundException(DiaryFailureInfo.DIARY_NOT_FOUND);
        }

        // DiaryListRes 응답 생성
        List<DiaryListRes.DiaryIdAndTitle> diaryIdAndTitleList = findDiaryEntityList.stream()
                .map(diaryEntity -> DiaryListRes.DiaryIdAndTitle.of(diaryEntity.getId(), diaryEntity.getUser().getNickname(), diaryEntity.getTitle(), diaryEntity.getCreatedAt()))
                .toList();
        return DiaryListRes.of(diaryIdAndTitleList);
    }

    public DiaryDetailInfoRes getDiaryDetailInfo(final Long id) {
        final DiaryEntity findDiary = findDiary(id);
        final String createTimeString = DateFormatUtil.format(findDiary.getCreatedAt()); //LocalDateTime -> String

        return DiaryDetailInfoRes.of(findDiary.getId(), findDiary.getTitle(), findDiary.getContent(), createTimeString);
    }

    @Transactional
    public void editDiaryContent(final Long id, final DiaryEditReq diaryEditReq) {
        final DiaryEntity findDiary = findDiary(id);
        findDiary.setContent(diaryEditReq.content()); //null 질문 답변 이후 처리
    }

    @Transactional
    public void deleteDiary(final Long id) {
        findDiary(id);
        diaryRepository.deleteById(id);
    }

    public DiaryEntity findDiary(final Long id) {
        return diaryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(DiaryFailureInfo.DIARY_NOT_FOUND)
        );
    }
}
