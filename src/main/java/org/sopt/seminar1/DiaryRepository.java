package org.sopt.seminar1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    void save(final Diary diary) {
        long id = numbering.addAndGet(1);
        this.storage.put(id, diary.getBody());
    }

    List<Diary> getAllDiary() {

        final List<Diary> diaryList = new ArrayList<>();

        //storage 돌면서 diaryList에 키,벨류 넣기
        storage.forEach((id, body) -> diaryList.add(new Diary(id, body)));

        //세미나 내용
//        for(long index = 1; index <= numbering.intValue(); index++) {
//            final String body = storage.get(index);
//
//            diaryList.add(new Diary(index, body));
//        }

        return diaryList;
    }
}
