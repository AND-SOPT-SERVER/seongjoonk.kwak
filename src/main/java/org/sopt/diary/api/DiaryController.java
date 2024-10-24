package org.sopt.diary.api;

import org.sopt.diary.api.dto.req.DiaryEditReq;
import org.sopt.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.api.dto.res.DiaryDetailInfoRes;
import org.sopt.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.common.Constants;
import org.sopt.diary.common.util.ValidatorUtil;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(final DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    //일기 작성 API
    @PostMapping("/diary")
    ResponseEntity<Void> postDiary(@RequestBody final DiaryPostReq diaryPostReq) {
        ValidatorUtil.validStringLength(diaryPostReq.content(), Constants.MAX_CONTENT_LENGTH);
        diaryService.createDiary(diaryPostReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/diaries")
    ResponseEntity<List<DiaryListRes>> getDiaryList() {
        final List<DiaryListRes> diaryList = diaryService.getDiaryList();
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
        diaryService.editDiaryContent(id, diaryEditReq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("diary/{id}")
    ResponseEntity<Void> deleteDiary(@PathVariable final Long id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
