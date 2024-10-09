package org.sopt.seminar1;

public class Validator {

    // String으로 들어온 id 검증하여 Long으로 출력
    public static Long validateId(String inputId) {
        try {
            long id = Long.parseLong(inputId);  // String을 long으로 변환
            if (id > 0) {  // ID는 0보다 큰 값이어야 함
                return id;
            } else {
                System.out.println("ID는 0보다 커야 합니다.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID는 숫자여야 합니다.");
            return null;
        }
    }
}
