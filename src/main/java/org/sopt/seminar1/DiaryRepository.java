package org.sopt.seminar1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DiaryRepository {
    private static final String DIARY_DB = "diaryDB.txt";

    // 모든 일기 불러오기
    List<Diary> getAllDiaryFromFile() {
        List<Diary> diaryList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DIARY_DB))) { //버퍼링으로 읽어서 줄단위 작업이 가능
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    Long id = Long.parseLong(parts[0].trim());
                    String body = parts[1].trim();
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

    // 새로운 일기 저장(일기 post할 때 append)
    void save(final Diary diary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIARY_DB, true))) {
            writer.write(diary.getId() + ":" + diary.getBody() + ":" + diary.isDeleted() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 일기 덮어쓰기(일기 삭제 or 수정했을 때 덮어쓰기)
    void saveOverwriteAllDiaryToFile(final List<Diary> diaryList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIARY_DB, false))) {
            for (Diary diary : diaryList) {
                writer.write(diary.getId() + ":" + diary.getBody() + ":" + diary.isDeleted() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}