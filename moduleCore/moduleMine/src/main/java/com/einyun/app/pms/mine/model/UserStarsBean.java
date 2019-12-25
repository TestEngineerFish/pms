package com.einyun.app.pms.mine.model;

public class UserStarsBean {
    private String userId;
    private String devideId;

    public UserStarsBean(String devideId) {
        this.devideId = devideId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
