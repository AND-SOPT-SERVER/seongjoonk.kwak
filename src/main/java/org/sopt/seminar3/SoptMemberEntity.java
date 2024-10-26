package org.sopt.seminar3;


import jakarta.persistence.*;

@Entity
@Table(name = "sopt_member")
public class SoptMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false) //여기있는 nullable 등등의 옵션은 처음에 테이블을 생성할때만 적용
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    public SoptMemberEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public SoptMemberEntity() {

    }

    @Override
    public String toString() {
        return this.name;
    }
}
