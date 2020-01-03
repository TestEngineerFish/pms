package com.einyun.app.common.model;

public class PicBean {
        private boolean success;
        private String  fileId;
        private String  fileName;
        private String  filePath;
        private int     size;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public PicUrlModel toPicUrlModel() {
        PicUrlModel picUrlModel = new PicUrlModel();
        picUrlModel.setId(fileId);
        picUrlModel.setPath(filePath);
        picUrlModel.setName(fileName);
        picUrlModel.setSize(size);

        return picUrlModel;
    }


}
