package com.einyun.app.library.resource.workorder.net.request;

public class ApplyCloseRequest {
    private String id;
    private String applyTaskId;
    private String instId;
    private String applyFiles;
    private String applicationDescription;
    private String taskId;
    private String endReason;
    private String messageType;
    private String ID_;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEndReason() {
        return endReason;
    }

    public void setEndReason(String endReason) {
        this.endReason = endReason;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getID() {
        return ID_;
    }

    public void setID(String ID) {
        this.ID_ = ID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyTaskId() {
        return applyTaskId;
    }

    public void setApplyTaskId(String applyTaskId) {
        this.applyTaskId = applyTaskId;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getApplyFiles() {
        return applyFiles;
    }

    public void setApplyFiles(String applyFiles) {
        this.applyFiles = applyFiles;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }
}
