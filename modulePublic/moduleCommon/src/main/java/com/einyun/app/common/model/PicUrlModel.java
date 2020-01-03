package com.einyun.app.common.model;

import com.google.gson.annotations.SerializedName;

public class PicUrlModel {
    private String id;
    private String name;
    private long size;
    @SerializedName(value = "path", alternate = "filePath")
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
