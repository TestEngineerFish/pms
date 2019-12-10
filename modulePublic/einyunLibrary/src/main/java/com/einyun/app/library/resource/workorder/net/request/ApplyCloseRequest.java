package com.einyun.app.library.resource.workorder.net.request;

public class ApplyCloseRequest {
    private String ID;
    private String taskID;
    private String proInsID;
    private String applyFiles;
    private String desc;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getProInsID() {
        return proInsID;
    }

    public void setProInsID(String proInsID) {
        this.proInsID = proInsID;
    }

    public String getApplyFiles() {
        return applyFiles;
    }

    public void setApplyFiles(String applyFiles) {
        this.applyFiles = applyFiles;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
