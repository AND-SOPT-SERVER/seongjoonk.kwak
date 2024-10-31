package org.sopt.diary.domain.diary.entity;

import jakarta.persistence.*;
import org.sopt.diary.domain.users.entity.User;
import org.sopt.diary.common.Category;
import org.sopt.diary.common.util.BaseTimeEntity;

@Entity
@Table(name = "diary")
public class DiaryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    public DiaryEntity() { }

    public static DiaryEntity create(final User user,
                                     final String title,
                                     final String content,
                                     final Category category,
                                     final boolean isPrivate) {
        return new DiaryEntity(user, title, content, category, isPrivate);
    }

    public DiaryEntity(User user, String title, String content, Category category, Boolean isPrivate) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
        this.category = category;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getPrivate() {
        return isPrivate;
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
