package org.sopt.diary.common.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //공통 정보가 필요할 때 부모 클래스에 선언된 필드를 상속받는 클래스에서 그대로 사용할 때 사용함. 이때, 부모 클래스에 대한 테이블은 별도로 생성X
@EntityListeners(AuditingEntityListener.class) //해당 어노테이션은 엔티티의 변화를 감지하여 엔티티와 매핑된 테이블의 데이터를 조작
public abstract class BaseTimeEntity {
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") //형식 지정
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
