package org.sopt.diary.domain.diary.repository;

import org.sopt.diary.common.Category;
import org.sopt.diary.domain.diary.entity.DiaryEntity;
import org.sopt.diary.domain.users.entity.User;
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

    // 글자 수 순으로 10개 조회 (ALL 카테고리) - 공개된 일기만 (네이티브 쿼리)
    @Query(value = "SELECT * FROM diary d WHERE d.is_private = false ORDER BY LENGTH(d.content) DESC LIMIT 10", nativeQuery = true)
    List<DiaryEntity> findTop10ByIsPrivateFalseOrderByContentLengthAscNative();

    // 카테고리가 있는 경우, 최신순으로 10개 조회 - 공개된 일기만
//    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category AND d.isPrivate = false ORDER BY d.createdAt DESC")
    List<DiaryEntity> findTop10ByCategoryAndIsPrivateFalseOrderByCreatedAtDesc(Category category);

    // 카테고리가 있는 경우, 글자 수 순으로 10개 조회 - 공개된 일기만
    @Query(value = "SELECT * FROM diary d WHERE d.category = :category AND d.is_private = false ORDER BY LENGTH(d.content) DESC LIMIT 10", nativeQuery = true)
    List<DiaryEntity> findTop10ByCategoryAndIsPrivateFalseOrderByContentLengthNative(@Param("category") Category category);

    // 사용자별 최신순으로 10개 조회 (ALL 카테고리)
    List<DiaryEntity> findTop10ByUserOrderByCreatedAtDesc(User user);

    // 사용자별 글자 수 순으로 10개 조회 (ALL 카테고리)
    @Query(value = "SELECT * FROM diary d WHERE d.user_id = :userId ORDER BY LENGTH(d.content) DESC LIMIT 10", nativeQuery = true)
    List<DiaryEntity> findTop10ByUserOrderByContentLengthAscNative(@Param("userId") Long userId);


    // 특정 카테고리의 사용자별 최신순으로 10개 조회
    List<DiaryEntity> findTop10ByUserAndCategoryOrderByCreatedAtDesc(User user, Category category);

    // 특정 카테고리의 사용자별 글자 수 순으로 10개 조회
    @Query(value = "SELECT * FROM diary d WHERE d.user_id = :userId AND d.category = :category ORDER BY LENGTH(d.content) DESC LIMIT 10", nativeQuery = true)
    List<DiaryEntity> findTop10ByUserAndCategoryOrderByContentLengthNative(@Param("userId") Long userId, @Param("category") Category category);



}



