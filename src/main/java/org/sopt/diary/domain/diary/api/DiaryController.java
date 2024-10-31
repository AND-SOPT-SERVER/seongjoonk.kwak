package org.sopt.diary.domain.diary.api;

import org.sopt.diary.domain.diary.api.dto.req.DiaryEditReq;
import org.sopt.diary.domain.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.domain.diary.api.dto.res.DiaryDetailInfoRes;
import org.sopt.diary.domain.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.common.Constants;
import org.sopt.diary.common.util.ValidatorUtil;
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
    ResponseEntity<Void> postDiary(@RequestHeader("userId") final Long userId,
                                   @RequestBody final DiaryPostReq diaryPostReq) {
        ValidatorUtil.validStringLength(diaryPostReq.content(), Constants.MAX_CONTENT_LENGTH);
        diaryService.createDiary(userId, diaryPostReq.title(), diaryPostReq.content(), diaryPostReq.category(), diaryPostReq.isPrivate());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //전체 일기 목록 조회 API
    @GetMapping("/diaries")
    ResponseEntity<DiaryListRes> getDiaryList(@RequestParam("category") final String category,
                                              @RequestParam("sort") final String sort) {
        final DiaryListRes diaryList = diaryService.getDiaryList(category, sort);
        return ResponseEntity.status(HttpStatus.OK).body(diaryList);
    }

    @GetMapping("/diary/{id}")
    ResponseEntity<DiaryDetailInfoRes> getDiaryDetailInfo(@PathVariable final Long id) {
        final DiaryDetailInfoRes diaryDetailInfoRes = diaryService.getDiaryDetailInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(diaryDetailInfoRes);
    }

    @PatchMapping("/diary/{id}")
    ResponseEntity<Void> editDiaryContent(@PathVariable final Long id,
                                          @RequestBody final DiaryEditReq diaryEditReq) {
        ValidatorUtil.validStringLength(diaryEditReq.content(), Constants.MAX_CONTENT_LENGTH);
        diaryService.editDiaryContent(id, diaryEditReq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("diary/{id}")
    ResponseEntity<Void> deleteDiary(@PathVariable final Long id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
