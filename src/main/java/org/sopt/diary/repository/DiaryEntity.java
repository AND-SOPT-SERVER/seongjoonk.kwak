package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.sopt.diary.common.util.BaseTimeEntity;

@Entity
public class DiaryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public DiaryEntity() { }

    public static DiaryEntity create(final String title, final String content) {
        return new DiaryEntity(title, content);
    }

    public DiaryEntity(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
