package org.sopt.seminar1;

public class Diary {
    private Long id;
    private final String body;
    private boolean isDeleted = false;

    public Diary(final Long id, final String body) {
        this.id = id;
        this.body = body;
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
