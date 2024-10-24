package org.sopt.diary.api;

import org.sopt.diary.api.dto.req.DiaryPostReq;
import org.sopt.diary.api.dto.res.DiaryListRes;
import org.sopt.diary.common.Constants;
import org.sopt.diary.common.ValidatorUtil;
import org.sopt.diary.service.DiaryService;
import org.sopt.seminar1.Diary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
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
    ResponseEntity<DiaryListRes> getDiaryList() {
        diaryService.
    }
}