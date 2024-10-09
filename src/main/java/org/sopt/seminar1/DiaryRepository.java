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
            Diary newDiary = new Diary(maxId, body);
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

                    //삭제되지 않은 일기들만 보이게
                    if (!isDeleted) {
                        Diary diary = new Diary(id, body);
                        diaryList.add(diary);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return diaryList;
    }

    void delete(Long diaryId) {
        List<Diary> existingDiaries = getAllDiary();

        //고민지점
        //1. 여기서 stream을 사용할 때, map과 toList로 새로운 List를 만드냐
        //2. 여기서 filter와 forEach를 사용해서 existingDiaries를 그냥 바꾸냐
        //현재 제 생각은 그래도 변수명에 의미를 좀 더 두어서, map과 toList를 이용해서 newDiaryList를 만들기로 생각
        List<Diary> newDiaryList = existingDiaries
                .stream()
                .map(diary -> {
                    if(diary.getId().equals(diaryId)) {
                        diary.setDeleted(true);
                    }
                    return diary;
                }).toList();
//                .filter(diary -> diaryId.equals(diary.getId()))
//                .forEach(diary -> diary.setDeleted(true));

        saveAllDiary(newDiaryList);
    }

    // 수정 or 삭제 시, 새로 파일 덮어쓰기
    void saveAllDiary(List<Diary> diaryList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIARY_DB, false))) {  // false: 덮어쓰기 모드
            for (Diary diary : diaryList) {
                writer.write(diary.getId() + ":" + diary.getBody() + ":" + diary.isDeleted() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
