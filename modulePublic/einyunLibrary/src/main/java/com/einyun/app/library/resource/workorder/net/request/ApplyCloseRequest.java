package com.einyun.app.library.resource.workorder.net.request;

public class ApplyCloseRequest {
    private String id;
    private String applyTaskId;
    private String instId;
    private String applyFiles;
    private String applicationDescription;

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
