package org.sopt.seminar1;

import java.io.*;
import java.time.LocalDateTime;

public class PatchInfoRepository {
    private static final String EDIT_INFO_DB = "editInfo.txt";

    // 수정 정보 저장
    void saveEditInfo(final PatchInfo patchData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EDIT_INFO_DB, false))) {
            writer.write(patchData.getPatchCount() + " / " + patchData.getLastPatchTime());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 수정 정보 불러오기
    PatchInfo loadEditInfo() {
        PatchInfo patchData = new PatchInfo(); // [0]: 수정 횟수, [1]: 마지막 수정 시간
        try (BufferedReader reader = new BufferedReader(new FileReader(EDIT_INFO_DB))) {
            String line;
            String lastLine = null;

            // 파일에서 마지막 줄을 찾기
            while ((line = reader.readLine()) != null) {
                lastLine = line; // 마지막 줄로 갱신
            }

            // 마지막 줄이 존재할 경우 처리
            if (lastLine != null) {
                String[] parts = lastLine.split("/");
                if (parts.length == 2) {
                    int patchCount = Integer.parseInt(parts[0].trim());  // 첫 번째 부분: 수정 횟수
                    LocalDateTime lastPatchedTime = LocalDateTime.parse(parts[1].trim());  // 두 번째 부분: 마지막 수정 시간

                    patchData.setPatchCount(patchCount); // 수정횟수
                    patchData.setLastPatchTime(lastPatchedTime); // 수정시간
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return patchData;
    }

}
