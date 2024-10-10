package org.sopt.seminar1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    // Grapheme Cluster(한 글자로 인식하는 단위)의 정규식, 검색할 패턴을 정의함
    // Grapheme Cluster는 사용자가 하나의 문자로 인식하는 유니코드 문자의 집합.
    // 결합된 이모지나 음성 기호가 붙은 문자는 여러 개의 코드 포인트로 이루어져 있지만, 이거를 하나로 인식하기 위함.
    private static final Pattern GRAPHEMEPATTERN = Pattern.compile("\\X");

    //위에서 만든 Pattern 객체를 통해 특정 문자열을 검색하여 패턴을 찾음
    private static final Matcher GRAPHEMEMATCHER = GRAPHEMEPATTERN.matcher("");

    //일기 최대 글자수
    private static final int MAX_DIARY_BODY_COUNT = 30;

    // String으로 들어온 id 검증하여 Long으로 출력
    public static Long validateId(final String inputId) {
        try {
            long id = Long.parseLong(inputId);  // String을 long으로 변환
            if (id > 0) {  // ID는 0보다 큰 값이어야 함
                return id;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    //글자수 검증
    public static boolean validateDiaryBodyLength(final String body) {
        int actualLength = getLengthOfEmojiContainableText(body); // 실제 글자 수를 계산
        if (actualLength > MAX_DIARY_BODY_COUNT) {
            System.out.println("일기의 글자수가 30자를 넘으면 안됩니다.");
            return false;
        } else {
            return true;
        }
    }

    //이모지 정규식 사용하여 이모지(단일), 이모지(결합)이든 뭐든 한 글자로 인식
    //Java의 String.length()는 문자열이 갖고 있는 유니코드 코드 단위, 즉 UTF-16 코드 유닛의 개수를 반환함 -> 2바이트(하나의 코드유닛)
    //이모지나 복합 문자(결합된 이모지, 일부 유니코드 문자 등)는 2바이트 이상의 길이를 가짐
    public static int getLengthOfEmojiContainableText(final String body) {
        if (body == null) {
            return 0;
        }

        //주어진 body에서 원하는 정규식 패턴을 찾기 위해 재설정
        GRAPHEMEMATCHER.reset(body);
        int count = 0;
        while (GRAPHEMEMATCHER.find()) {
            count++;
        }
        return count;
    }
}


