package org.sopt.seminar1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
            writer.write(newDiary.getId() + ":" + newDiary.getBody() + "\n");
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

                if(parts.length == 2) {
                    Long id = Long.parseLong(parts[0].trim());  // ID
                    String body = parts[1].trim();  // 일기 내용

                    Diary diary = new Diary(id, body);
                    diaryList.add(diary);
                }
            }
        } catch (IOException e) {
        }
        return diaryList;
    }
}
