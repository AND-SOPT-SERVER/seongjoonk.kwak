package org.sopt.seminar1;

import java.util.ArrayList;
import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();
    private final List<Diary> storage = new ArrayList<>(); // 메모리 저장소

    public DiaryService() {
        // 프로그램 시작 시, 파일에서 읽어와 storage에 저장
        storage.addAll(diaryRepository.getAllDiaryFromFile());
    }

    void postDiary(final String body) {
        long newId = storage.stream().mapToLong(Diary::getId).max().orElse(0) + 1;
        Diary newDiary = new Diary(newId, body, false);
        storage.add(newDiary);
        diaryRepository.saveAllDiaryToFile(storage); // 파일에 저장
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
            diaryRepository.saveAllDiaryToFile(storage); // 파일에 저장
        } else {
            System.out.println("해당 ID의 일기를 찾을 수 없습니다.");
        }
    }

    void patchDiary(final Long id, final String body) {
        Diary diaryToPatch = findDiaryById(id);
        if (diaryToPatch != null) {
            if (!diaryToPatch.isDeleted()) {
                diaryToPatch.setBody(body);
                diaryRepository.saveAllDiaryToFile(storage); // 파일에 저장
            } else {
                System.out.println("삭제된 일기는 수정할 수 없습니다.");
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
                diaryRepository.saveAllDiaryToFile(storage); // 파일에 저장
                System.out.println(diaryToRestore.getId() + "번 일기가 복구되었습니다.");
            } else {
                System.out.println("삭제되지 않은 일기입니다.");
            }
        } else {
            System.out.println("해당 ID의 일기를 찾을 수 없습니다.");
        }
    }

    // ID로 일기를 찾는 메서드
    private Diary findDiaryById(final Long id) {
        return storage.stream()
                .filter(diary -> diary.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
