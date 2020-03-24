package com.einyun.app.common.model;

import com.einyun.app.base.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PushResultModel {
    private String type;
    private String subType;
    private String content;
    private String msgId;
    private String _ALIYUN_NOTIFICATION_ID_;

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

    public String get_ALIYUN_NOTIFICATION_ID_() {
        return _ALIYUN_NOTIFICATION_ID_;
    }

    public void set_ALIYUN_NOTIFICATION_ID_(String _ALIYUN_NOTIFICATION_ID_) {
        this._ALIYUN_NOTIFICATION_ID_ = _ALIYUN_NOTIFICATION_ID_;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public class Content{
        private String cateName;
        private String taskId;
        private String procInstId;

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public String getTaskId() {
            return taskId==null?"":taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getInstId() {
            return procInstId;
        }

        public void setInstId(String instId) {
            this.procInstId = instId;
        }
    }
}
