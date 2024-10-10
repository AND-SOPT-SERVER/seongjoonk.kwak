package org.sopt.seminar1;

import java.util.List;

public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();

    Status getStatus() {
        return status;
    }

    void boot() {
        this.status = Status.RUNNING;
    }

    void finish() {
        this.status = Status.FINISHED;
    }

    final List<Diary> getList() {
        return diaryService.getAllDiary();
    }

    final void post(final String body) {
        // 글자수 검증
        if (Validator.validateDiaryBodyLength(body)) {
            diaryService.postDiary(body);
        }
    }

    final void delete(final String id) {
        // String으로 들어온 id 검증
        final Long diaryId = validateIdAndChangeToLong(id);
        if(diaryId != null) {
            diaryService.deleteDiary(diaryId);
        }
    }

    final void patch(final String id, final String body) {
        final Long diaryId = validateIdAndChangeToLong(id);

        // 글자수 검증
        if (diaryId != null && Validator.validateDiaryBodyLength(body)) {
            diaryService.patchDiary(diaryId, body);
        }
    }

    final void restore(final String id) {
        final Long diaryId = validateIdAndChangeToLong(id);
        if (diaryId != null) {
            diaryService.restore(diaryId);
        }
    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }

    private Long validateIdAndChangeToLong(final String id) {
        if(Validator.validateId(id) == null) {
            System.out.println("잘못된 ID입니다.");
            return null;
        } else {
        return Validator.validateId(id);
        }
    }
}