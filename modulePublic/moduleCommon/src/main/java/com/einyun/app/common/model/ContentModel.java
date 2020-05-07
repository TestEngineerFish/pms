package com.einyun.app.common.model;

public class ContentModel {

    /**
     * procInstId : 77227663067971598
     * taskId : 77227663067995150
     */

    private String procInstId;
    private String taskId;
    private String cateName="户内报修类";
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCateName() {
        return cateName==null?"":cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
