package org.sopt.diary.domain.diary.repository;

import org.sopt.diary.common.Category;
import org.sopt.diary.domain.diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    // 최신순으로 10개 조회 (ALL 카테고리) - 공개된 일기만
    List<DiaryEntity> findTop10ByIsPrivateFalseOrderByCreatedAtDesc();

    // 글자 수 순으로 10개 조회 (ALL 카테고리) - 공개된 일기만
    @Query("SELECT d FROM DiaryEntity d WHERE d.isPrivate = false ORDER BY LENGTH(d.content) ASC")
    List<DiaryEntity> findTop10ByOrderByContentLength();

    // 카테고리가 있는 경우, 최신순으로 10개 조회 - 공개된 일기만
    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category AND d.isPrivate = false ORDER BY d.createdAt DESC")
    List<DiaryEntity> findTop10ByCategoryAndIsPrivateFalseOrderByCreatedAtDesc(@Param("category") Category category);

    // 카테고리가 있는 경우, 글자 수 순으로 10개 조회 - 공개된 일기만
    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category AND d.isPrivate = false ORDER BY LENGTH(d.content) ASC")
    List<DiaryEntity> findTop10ByCategoryAndIsPrivateFalseOrderByContentLength(@Param("category") Category category);
}



