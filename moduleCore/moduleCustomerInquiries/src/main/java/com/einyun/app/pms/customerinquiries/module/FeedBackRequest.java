package com.einyun.app.pms.customerinquiries.module;

public class FeedBackRequest {

    /**
     * account : ceshi001
     * actionName : commu
     * notifyType : inner,app_push
     * opinion : 反馈内容
     * taskId : 1302601
     */

    private String account;
    private String actionName;
    private String notifyType;
    private String opinion;
    private String taskId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
