package org.sopt.seminar1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();
    private final List<Diary> storage = new ArrayList<>(); // 메모리 저장소
    private int editCount; // 수정 횟수
    private LocalDateTime lastEditTime; // 마지막 수정 시간

    public DiaryService() {
        // 프로그램 시작 시, 파일에서 읽어와 storage에 저장
        storage.addAll(diaryRepository.getAllDiaryFromFile());
        int[] editInfo = diaryRepository.loadEditInfo();
        this.editCount = editInfo[0];
        this.lastEditTime = LocalDateTime.now(); // 현재 시간으로 초기화
    }

    void postDiary(final String body) {
        long newId = storage.stream().mapToLong(Diary::getId).max().orElse(0) + 1;
        Diary newDiary = new Diary(newId, body, false);
        storage.add(newDiary);
        diaryRepository.save(newDiary); // 파일에 저장
    }

    List<Diary> getAllDiary() {
        return storage.stream()
                .map(diary -> {
                    if(diary.isDeleted()) {
                        return new Diary(diary.getId(), "삭제일기입니다.", true);
                    }
                    return new Diary(diary.getId(), diary.getBody(), diary.isDeleted());
                })
                .toList();
    }

    void deleteDiary(final Long id) {
        Diary diaryToDelete = findDiaryById(id);
        if (diaryToDelete != null) {
            diaryToDelete.setDeleted(true);
            diaryRepository.saveOverwriteAllDiaryToFile(storage); // 파일에 저장
        } else {
            System.out.println("해당 ID의 일기를 찾을 수 없습니다.");
        }
    }

    void patchDiary(final Long id, final String body) {
        Diary diaryToPatch = findDiaryById(id);
        if (diaryToPatch != null) {
            LocalDateTime now = LocalDateTime.now();
            if (!diaryToPatch.isDeleted() && canEdit(now)) {
                diaryToPatch.setBody(body);
                diaryRepository.saveOverwriteAllDiaryToFile(storage); // 파일에 저장
                editCount++;
                lastEditTime = now;
                diaryRepository.saveEditInfo(editCount, lastEditTime.format(DateTimeFormatter.ISO_DATE_TIME)); // 수정 정보 저장
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
        if (diaryToRestore != null) {
            if (diaryToRestore.isDeleted()) {
                diaryToRestore.setDeleted(false);
                diaryRepository.saveOverwriteAllDiaryToFile(storage); // 파일에 저장
                System.out.println(diaryToRestore.getId() + "번 일기가 복구되었습니다.");
            } else {
                System.out.println("삭제되지 않은 일기입니다.");
            }
        } else {
            System.out.println("해당 ID의 일기를 찾을 수 없습니다.");
        }
    }

    // 수정 가능 여부 확인
    private boolean canEdit(LocalDateTime now) {
        if (lastEditTime == null || lastEditTime.toLocalDate().isBefore(now.toLocalDate())) {
            editCount = 0; // 새로운 날이면 수정 횟수 초기화
            return true;
        }
        return editCount < 2; // 하루에 두 번 수정 가능
    }

    // ID로 일기를 찾는 메서드
    private Diary findDiaryById(final Long id) {
        return storage.stream()
                .filter(diary -> diary.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}