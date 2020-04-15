package com.einyun.app.pms.mine.model;

public class MsgExtendVars {

    /**
     * type : reminder
     * subType : complain
     * content : {"taskId":"74091611092420614"}
     */

    private String type;
    private String subType;
    private ContentBean content;

    public String getType() {
        return type==null?"":type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType==null?"":subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * taskId : 74091611092420614
         */

        private String taskId;
        private String procInstId;
        private String cateName;

        public String getTaskId() {
            return taskId==null?"":taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getProcInstId() {
            return procInstId==null?"":procInstId;
        }

        public void setProcInstId(String procInstId) {
            this.procInstId = procInstId;
        }

        public String getCateName() {
            return cateName==null?"":cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }
    }
}
