package org.sopt.seminar1;

public class Diary {
    private Long id;
    private String body;
    private boolean isDeleted;

    public Diary(final Long id, final String body, final boolean isDeleted) {
        this.id = id;
        this.body = body;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
