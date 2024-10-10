package org.sopt.seminar1;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DiaryRepository {
    private static final String DIARY_DB = "diaryDB.txt";
    private static final String EDIT_INFO_DB = "editInfo.txt";

    // 모든 일기 불러오기
    List<Diary> getAllDiaryFromFile() {
        List<Diary> diaryList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DIARY_DB))) {
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

    // 새로운 일기 저장 (일기 삭제 or 수정했을 때 덮어쓰기)
    void saveOverwriteAllDiaryToFile(final List<Diary> diaryList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIARY_DB, false))) {
            for (Diary diary : diaryList) {
                writer.write(diary.getId() + ":" + diary.getBody() + ":" + diary.isDeleted() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 수정 정보 저장
    void saveEditInfo(int editCount, String lastEditTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EDIT_INFO_DB))) {
            writer.write(editCount + "\n");
            writer.write(lastEditTime);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 수정 정보 불러오기
    int[] loadEditInfo() {
        int[] editInfo = new int[2]; // [0]: 수정 횟수, [1]: 마지막 수정 시간
        try (BufferedReader reader = new BufferedReader(new FileReader(EDIT_INFO_DB))) {
            editInfo[0] = Integer.parseInt(reader.readLine());
            editInfo[1] = LocalDateTime.parse(reader.readLine()).getHour();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return editInfo;
    }

    //새로운 일기 저장(일기 post할 때 append)
    void save(final Diary diary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIARY_DB, true))) {
            writer.write(diary.getId() + ":" + diary.getBody() + ":" + diary.isDeleted() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}