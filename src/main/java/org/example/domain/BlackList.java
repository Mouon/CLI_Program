package org.example.domain;

public class BlackList {

    private String userId;
    private Long overDueDate;

    public BlackList(String userId, Long overDueDate) {
        this.userId = userId;
        this.overDueDate = overDueDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getOverDueDate() {
        return overDueDate;
    }

    public void setOverDueDate(Long overDueDate) {
        this.overDueDate = overDueDate;
    }
}
