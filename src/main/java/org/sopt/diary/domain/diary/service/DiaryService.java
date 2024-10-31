package org.sopt.diary.domain.diary.service;

import org.sopt.diary.common.Category;
import org.sopt.diary.common.Failure.UserFailureInfo;
import org.sopt.diary.common.SortBy;
import org.sopt.diary.common.util.ValidatorUtil;
import org.sopt.diary.domain.diary.api.dto.req.DiaryEditReq;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DiaryService(DiaryRepository diaryRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    //일기 작성
    @Transactional
    public void createDiary(final Long userId,
                            final String title,
                            final String content,
                            final String category,
                            final boolean isPrivate) {
        final User foundUser = findUser(userId);
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
                case QUANTITY -> diaryRepository.findTop10ByIsPrivateFalseOrderByContentLengthAscNative(); //글자수순 정렬
            };
        } else { //카테고리별로 조회
            findDiaryEntityList = switch (sortByEnum) {
                case LATEST ->
                        diaryRepository.findTop10ByCategoryAndIsPrivateFalseOrderByCreatedAtDesc(categoryEnum); //최신순 정렬
                case QUANTITY ->
                        diaryRepository.findTop10ByCategoryAndIsPrivateFalseOrderByContentLengthNative(categoryEnum); //글자수순 정렬
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

    //일기 상세 조회
    public DiaryDetailInfoRes getDiaryDetailInfo(final Long userId, final Long diaryId) {
        final User foundUser = findUser(userId);
        final DiaryEntity findDiary = findDiary(diaryId);

        //일기의 주인이 맞는지 검증
        isDiaryByUser(foundUser, findDiary);

        final String createTimeString = DateFormatUtil.format(findDiary.getCreatedAt()); //LocalDateTime -> String
        return DiaryDetailInfoRes.of(findDiary.getId(), findDiary.getTitle(), findDiary.getContent(), createTimeString, String.valueOf(findDiary.getCategory()));
    }

    //일기 수정
    @Transactional
    public void editDiary(final Long userId, final Long diaryId, final DiaryEditReq diaryEditReq) {
        final User foundUser = findUser(userId);
        final DiaryEntity findDiary = findDiary(diaryId);

        //일기 작성자인지 검증
        isDiaryByUser(foundUser, findDiary);

        findDiary.setContent(diaryEditReq.content());
        findDiary.setCategory(diaryEditReq.category());
    }

    //일기 삭제
    @Transactional
    public void deleteDiary(final Long userId, final Long diaryId) {
        final User foundUser = findUser(userId);
        final DiaryEntity foundDiary = findDiary(diaryId);

        //일기 작성자 검증
        isDiaryByUser(foundUser, foundDiary);

        diaryRepository.deleteById(diaryId);
    }

    //일기 찾기
    public DiaryEntity findDiary(final Long diayId) {
        return diaryRepository.findById(diayId).orElseThrow(
                () -> new NotFoundException(DiaryFailureInfo.DIARY_NOT_FOUND)
        );
    }

    //유저 찾기
    public User findUser(final Long uuserId) {
        return userRepository.findById(uuserId).orElseThrow(
                () -> new NotFoundException(UserFailureInfo.USER_NOT_FOUND)
        );
    }

    //일기의 주인이 맞는지 검증
    private void isDiaryByUser(final User user, final DiaryEntity diary) {
        if (!diary.getUser().equals(user)) {
            throw new NotFoundException(DiaryFailureInfo.UNAUTHORIZED_EXCEPTION);
        }
    }
}
