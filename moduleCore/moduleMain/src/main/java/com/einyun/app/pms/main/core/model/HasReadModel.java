package com.einyun.app.pms.main.core.model;

public class HasReadModel {

    /**
     * msgFlag : false
     * lastListTime : 2020-03-11 15:16:48
     * currentTime : 2020-03-11 15:26:42
     */

    private boolean msgFlag;
    private String lastListTime;
    private String currentTime;

    public boolean isMsgFlag() {
        return msgFlag;
    }

    public void setMsgFlag(boolean msgFlag) {
        this.msgFlag = msgFlag;
    }

    public String getLastListTime() {
        return lastListTime;
    }

    public void setLastListTime(String lastListTime) {
        this.lastListTime = lastListTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
