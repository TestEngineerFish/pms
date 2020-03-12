package com.einyun.app.pms.mine.model;

public class MsgModel {


    /**
     * createOrgId : null
     * id : 73705805991391238
     * subject : 工单处理提醒
     * ownerId : null
     * owner : 缪燕宇
     * messageType : app_push
     * canReply : 0
     * isPublic : 0
     * content : null
     * fileMsg : null
     * receiverName : null
     * receiveTime : 2020-03-02 11:13:45
     * receiverId : null
     * receiverOrgName : null
     * receiverOrgId : null
     * rid : 73705805991392262
     * detailUrl : null
     * extendVars : {"测试主题一 ":123,"测试主题三 ":"分为一发了两奇偶接口绿色的 ","测试主题二 ":"封口机弗兰克微积分来看文件我 ","测试主题四 ":true}
     * templateKey : sss
     * hasRead : false
     */

    private String createOrgId;
    private String id;
    private String subject;
    private String ownerId;
    private String owner;
    private String messageType;
    private int canReply;
    private int isPublic;
    private String content;
    private Object fileMsg;
    private String receiverName;
    private String receiveTime;
    private String sendTime;
    private String receiverId;
    private String receiverOrgName;
    private String receiverOrgId;
    private String rid;
    private String detailUrl;
    private String extendVars;
    private String templateKey;
    private boolean hasRead;




    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverOrgName() {
        return receiverOrgName;
    }

    public void setReceiverOrgName(String receiverOrgName) {
        this.receiverOrgName = receiverOrgName;
    }

    public String getReceiverOrgId() {
        return receiverOrgId;
    }

    public void setReceiverOrgId(String receiverOrgId) {
        this.receiverOrgId = receiverOrgId;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getCanReply() {
        return canReply;
    }

    public void setCanReply(int canReply) {
        this.canReply = canReply;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }



    public Object getFileMsg() {
        return fileMsg;
    }

    public void setFileMsg(Object fileMsg) {
        this.fileMsg = fileMsg;
    }


    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }



    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }


    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getExtendVars() {
        return extendVars;
    }

    public void setExtendVars(String extendVars) {
        this.extendVars = extendVars;
    }

    public static class ExtendVarsBean {
        /**
         * 测试主题一  : 123
         * 测试主题三  : 分为一发了两奇偶接口绿色的
         * 测试主题二  : 封口机弗兰克微积分来看文件我
         * 测试主题四  : true
         */

        private int 测试主题一;
        private String 测试主题三;
        private String 测试主题二;
        private boolean 测试主题四;

        public int get测试主题一() {
            return 测试主题一;
        }

        public void set测试主题一(int 测试主题一) {
            this.测试主题一 = 测试主题一;
        }

        public String get测试主题三() {
            return 测试主题三;
        }

        public void set测试主题三(String 测试主题三) {
            this.测试主题三 = 测试主题三;
        }

        public String get测试主题二() {
            return 测试主题二;
        }

        public void set测试主题二(String 测试主题二) {
            this.测试主题二 = 测试主题二;
        }

        public boolean is测试主题四() {
            return 测试主题四;
        }

        public void set测试主题四(boolean 测试主题四) {
            this.测试主题四 = 测试主题四;
        }
    }
}
