package org.sopt.seminar1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DiaryRepository {
    private static final String DIARY_DB = "diaryDB.txt";

    // 모든 일기 불러오기
    List<Diary> getAllDiaryFromFile() {
        List<Diary> diaryList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DIARY_DB))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");

                if (parts.length == 3) {
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

    // 새로운 일기 저장 (파일에 저장)
    void saveAllDiaryToFile(final List<Diary> diaryList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIARY_DB, false))) {  // false: 덮어쓰기 모드
            for (Diary diary : diaryList) {
                writer.write(diary.getId() + ":" + diary.getBody() + ":" + diary.isDeleted() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}