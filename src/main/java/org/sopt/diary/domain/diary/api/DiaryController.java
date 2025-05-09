package org.sopt.diary.domain.diary.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.diary.common.enums.Category;
import org.sopt.diary.common.enums.SortBy;
import org.sopt.diary.common.enums.validation.EnumValue;
import org.sopt.diary.domain.diary.api.dto.req.DiaryEditReq;
import org.sopt.diary.domain.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.domain.diary.api.dto.res.DiaryDetailInfoRes;
import org.sopt.diary.domain.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.common.Constants;
import org.sopt.diary.common.util.ValidatorUtil;
import org.sopt.diary.domain.diary.api.dto.res.DiaryMyListRes;
import org.sopt.diary.domain.diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    //일기 작성 API
    @PostMapping("/diary")
    ResponseEntity<Void> postDiary(@NotNull @Min(1) @RequestHeader("userId") final long userId,
                                   @Valid @RequestBody final DiaryPostReq diaryPostReq) {
        ValidatorUtil.validStringLength(diaryPostReq.content(), Constants.MAX_CONTENT_LENGTH);
        diaryService.createDiary(userId, diaryPostReq.title(), diaryPostReq.content(), diaryPostReq.category(), diaryPostReq.isPrivate());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //전체 일기 목록 조회 API
    @GetMapping("/diaries")
    ResponseEntity<DiaryListRes> getDiaryList(
            @EnumValue(enumClass = Category.class, message = "유효하지 않은 카테고리입니다", ignoreCase = false)
            @RequestParam("category")
            final String category,
            @EnumValue(enumClass = SortBy.class, message = "유효하지 않은 카테고리입니다", ignoreCase = false)
            @RequestParam("sort")
            final String sort) {
        final DiaryListRes diaryList = diaryService.getDiaryList(category, sort);
        return ResponseEntity.status(HttpStatus.OK).body(diaryList);
    }

    //개인 일기 목록 조회 API
    @GetMapping("/diaries/my")
    ResponseEntity<DiaryMyListRes> getMyDiaryList(
            @NotNull
            @Min(1)
            @RequestHeader("userId")
            final long userId,
            @EnumValue(enumClass = Category.class, message = "유효하지 않은 카테고리입니다", ignoreCase = false)
            @RequestParam("category")
            final String category,
            @EnumValue(enumClass = SortBy.class, message = "유효하지 않은 카테고리입니다", ignoreCase = false)
            @RequestParam("sort") final String sort) {
        final DiaryMyListRes diaryMyListRes = diaryService.getMyDiaryList(userId, category, sort);
        return ResponseEntity.status(HttpStatus.OK).body(diaryMyListRes);
    }

    //일기 상세 조회 API
    @GetMapping("/diary/{diaryId}")
    ResponseEntity<DiaryDetailInfoRes> getDiaryDetailInfo(@NotNull @Min(1) @RequestHeader("userId") final long userId,
                                                          @NotBlank @PathVariable final Long diaryId) {
        final DiaryDetailInfoRes diaryDetailInfoRes = diaryService.getDiaryDetailInfo(userId, diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryDetailInfoRes);
    }

    //일기 수정 api
    @PatchMapping("/diary/{diaryId}")
    ResponseEntity<Void> editDiary(@NotNull @Min(1) @RequestHeader("userId") final long userId,
                                   @NotBlank @PathVariable final Long diaryId,
                                   @Valid @RequestBody final DiaryEditReq diaryEditReq) {
        ValidatorUtil.validStringLength(diaryEditReq.content(), Constants.MAX_CONTENT_LENGTH);
        diaryService.editDiary(userId, diaryId, diaryEditReq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("diary/{diaryId}")
    ResponseEntity<Void> deleteDiary(@NotNull @Min(1) @RequestHeader("userId") final long userId,
                                     @NotBlank @PathVariable final Long diaryId) {
        diaryService.deleteDiary(userId, diaryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
