package org.sopt.seminar1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final PatchInfoRepository patchInfoRepository;
    private final List<Diary> storage = new ArrayList<>(); // 메모리 저장소
    private final PatchInfo patchData;

    public DiaryService() {

        diaryRepository = new DiaryRepository();
        patchInfoRepository = new PatchInfoRepository();

        // 프로그램 시작 시, 파일에서 읽어와 저장
        storage.addAll(diaryRepository.getAllDiaryFromFile());
        patchData = patchInfoRepository.loadPatchInfo();
    }

    void postDiary(final String body) {
        final long newId = storage.stream().mapToLong(Diary::getId).max().orElse(0) + 1;
        Diary newDiary = new Diary(newId, body, false);
        storage.add(newDiary);
        diaryRepository.save(newDiary); // 파일에 저장
    }

    //삭제된 일기일 경우, body의 출력값을 삭제된 일기로 보이게함(실제 DB는 원래 값)
    List<Diary> getAllDiary() {
        return storage.stream()
                .map(diary -> {
                    if(diary.isDeleted()) {
                        return new Diary(diary.getId(), "삭제된 일기입니다.", true);
                    }
                    return new Diary(diary.getId(), diary.getBody(), diary.isDeleted());
                })
                .toList();
    }

    void deleteDiary(final Long id) {
        Diary diaryToDelete = findDiaryById(id);
        if (!isDiaryNull(diaryToDelete)) {
            diaryToDelete.setDeleted(true);
            overWriteDiaryToFile(storage);
        } else {
            System.out.println("해당 ID의 일기를 찾을 수 없습니다.");
        }
    }

    void patchDiary(final Long id, final String body) {
        Diary diaryToPatch = findDiaryById(id);
        if (!isDiaryNull(diaryToPatch)) {
            final LocalDateTime now = LocalDateTime.now();

            // 고민 지점 : 이것도 검증(Validator)에 넣어야되나?
            if (!diaryToPatch.isDeleted() && canEdit(now, patchData)) {

                //수정된 일기 저장
                diaryToPatch.setBody(body);
                overWriteDiaryToFile(storage);

                //patch정보 파일에 저장
                patchData.setPatchCount(patchData.getPatchCount() + 1);
                patchData.setLastPatchTime(now);
                patchInfoRepository.savePatchInfo(patchData);
            } else if (diaryToPatch.isDeleted()) {
                 System.out.println("삭제된 일기는 수정할 수 없습니다.");
            } else {
                System.out.println("하루에 두 번만 수정할 수 있습니다.");
            }
        } else {
            System.out.println("해당 ID의 일기를 찾을 수 없습니다.");
        }
    }

    void restore(final Long id) {
        Diary diaryToRestore = findDiaryById(id);
        if (!isDiaryNull(diaryToRestore)) {

            if (diaryToRestore.isDeleted()) {
                diaryToRestore.setDeleted(false);
                overWriteDiaryToFile(storage);
                System.out.println(diaryToRestore.getId() + "번 일기가 복구되었습니다.");
            } else {
                System.out.println("삭제되지 않은 일기입니다.");
            }
        } else {
            System.out.println("해당 ID의 일기를 찾을 수 없습니다.");
        }
    }

    // 수정 가능 여부 확인
    private boolean canEdit(final LocalDateTime now, final PatchInfo patchData) {
        //새로운 날짜이면 수정 횟수 초기화
        if (patchData.getLastPatchTime() == null || patchData.getLastPatchTime().toLocalDate().isBefore(now.toLocalDate())) {
            patchData.setPatchCount(0);
            return true;
        }
        return patchData.getPatchCount() < 2; // 하루에 두 번 수정 가능
    }

    // ID로 일기를 찾는 메서드
    private Diary findDiaryById(final Long id) {
        return storage.stream()
                .filter(diary -> diary.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private boolean isDiaryNull(final Diary diary) {
        return diary == null;
    }

    private void overWriteDiaryToFile(final List<Diary> diaryList) {
        diaryRepository.saveOverwriteAllDiaryToFile(diaryList);
    }
}