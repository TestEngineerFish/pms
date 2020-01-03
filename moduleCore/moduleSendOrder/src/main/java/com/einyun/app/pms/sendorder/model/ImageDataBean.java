package com.einyun.app.pms.sendorder.model;

public class ImageDataBean {

    /**
     * fileId : 66958619601534981
     * name : Screenshot_20191223_174019_com.android.keyguard.jpg
     * path : 55614223698362369/xll/2019/12/66958619601534981.jpg
     * size : 200219
     * success : true
     */

    private String fileId;
    private String name;
    private String path;
    private int size;
    private boolean success;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
