package com.einyun.app.pms.main.core.model;

public class UserStarsBean {
    private String userId;
    private String devideId;

    public UserStarsBean(String devideId,String userId) {
        this.devideId = devideId;
        this.userId=userId;
    }
    public UserStarsBean() {

    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDevideId() {
        return devideId;
    }

    public void setDevideId(String devideId) {
        this.devideId = devideId;
    }
}
