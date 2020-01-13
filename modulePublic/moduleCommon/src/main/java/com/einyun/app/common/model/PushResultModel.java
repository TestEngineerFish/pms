package com.einyun.app.common.model;

import com.einyun.app.base.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PushResultModel {
    private String type;
    private String subType;
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    Gson gson= new Gson();
    public Content getContent() {
        if (StringUtil.isNullStr(content)){
            return gson.fromJson(content, new TypeToken<Content>() {
            }.getType());
        }
        return null;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public class Content{
        private String cateName;
        private String taskId;
        private String instId;

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getInstId() {
            return instId;
        }

        public void setInstId(String instId) {
            this.instId = instId;
        }
    }
}
