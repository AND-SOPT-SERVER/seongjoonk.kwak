package org.sopt.seminar1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    //Grapheme Cluster(한 글자로 인식하는 단위)의 정규식, 검색할 패턴을 정의함
    private static final Pattern GRAPHEMEPATTERN = Pattern.compile("\\X");

    //위에서 만든 Pattern 객체를 통해 특정 문자열을 검색하여 패턴을 찾음
    private static final Matcher GRAPHEMEMATCHER = GRAPHEMEPATTERN.matcher("");

    //일기 최대 글자수
    private static final int MAX_DIARY_BODY_COUNT = 30;

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

    //글자수 검증
    public static boolean validateDiaryBodyLength(final String body) {
        int actualLength = getLengthOfEmojiContainableText(body); // 실제 글자 수를 계산
        System.out.println(actualLength);
        if (actualLength > MAX_DIARY_BODY_COUNT) {
            System.out.println("일기의 글자수가 30자를 넘으면 안됩니다.");
            return false;
        } else {
            return true;
        }
    }

    //이모지 정규식 사용하여 이모지(단일), 이모지(결합)이든 뭐든 한 글자로 인식
    public static int getLengthOfEmojiContainableText(String text) {
        if (text == null) {
            return 0;
        }
        GRAPHEMEMATCHER.reset(text);
        int count = 0;
        while (GRAPHEMEMATCHER.find()) {
            count++;
        }
        return count;
    }
}


