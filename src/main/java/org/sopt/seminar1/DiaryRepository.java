package org.sopt.seminar1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiaryRepository {
    private static final String DIARY_DB = "diaryDB.txt";
    private long maxId;

    // 프로그램 시작 시, 파일을 읽어서 현재 저장된 최대 ID 값을 찾는다.
    public DiaryRepository() {
        List<Diary> existingDiaries = getAllDiary();
        if (!existingDiaries.isEmpty()) {
            maxId = existingDiaries.stream().mapToLong(Diary::getId).max().orElse(0);
        } else {
            maxId = 0;
        }
    }

    //새로운 일기 저장
    void save(final String body) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIARY_DB, true))) { //true : append시킴
            maxId ++;
            Diary newDiary = new Diary(maxId, body, false);
            writer.write(newDiary.getId() + ":" + newDiary.getBody() + ":" + newDiary.isDeleted() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 모든 일기 불러오기
    List<Diary> getAllDiary() {
        List<Diary> diaryList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DIARY_DB))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");

                if(parts.length == 3) {
                    Long id = Long.parseLong(parts[0].trim());  // ID
                    String body = parts[1].trim();  // 일기 내용
                    boolean isDeleted = Boolean.parseBoolean(parts[2].trim());

                    Diary diary = new Diary(id, body, isDeleted);
                    diaryList.add(diary);

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return diaryList;
    }

    //일기 삭제(soft delete)
    void delete(final Long diaryId) {
        List<Diary> diaryList = getAllDiary();

        diaryList.stream()
                .filter(diary -> diaryId.equals(diary.getId()))
                .forEach(diary -> diary.setDeleted(true));

        saveAllDiary(diaryList);
    }

    //일기 수정
    void patch(final Long diaryId, final String newBody) {
        List<Diary> diaryList = getAllDiary();

        diaryList.stream()
                .filter(diary -> diaryId.equals(diary.getId()))
                .forEach(diary -> {
                    if (diary.isDeleted()) {
                        System.out.println("삭제된 일기는 수정할 수 없습니다.");
                    } else {
                        diary.setBody(newBody);
                    }
                });
        saveAllDiary(diaryList);
    }

    // 수정 or 삭제 시, 새로 파일 덮어쓰기
    void saveAllDiary(final List<Diary> diaryList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIARY_DB, false))) {  // false: 덮어쓰기 모드
            //foreach를 쓰면 람다 표현식 내부에서 예외가 발생했을 경우, java는 람다 안에서 예외 처리를 할 수 없음
            //forEach를 쓰면 try-catch문을 안에 한번 더 적어야됨
            for (Diary diary : diaryList) {
                writer.write(diary.getId() + ":" + diary.getBody() + ":" + diary.isDeleted() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
