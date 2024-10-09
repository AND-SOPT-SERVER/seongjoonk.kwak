package org.sopt.seminar1;

import java.util.ArrayList;
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

    // APIS
    final List<Diary> getList() {
        return diaryService.getAllDiary();
    }

    final void post(final String body) {
        diaryService.postDiary(body);
    }

    final void delete(final String id) {

        // String으로 들어온 id 검증
        Long diaryId = Validator.validateId(id);
        diaryService.deleteDiary(diaryId);
    }

    final void patch(final String id, final String body) {

    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}
