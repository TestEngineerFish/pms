package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "distributes",primaryKeys = {"ID_","userId","orderType"})
public class Distribute{
    public final static int ORDER_TYPE_PENDING=1;
    public final static int ORDER_TYPE_DONE=2;
    private String F_CHECK_CONTENT;
    private String subject;
    private String F_TYPE;
    private String F_PROJECT_ID;
    private String ownerId;
    private String F_PROJECT_NAME;
    private String proInsId;
    private String F_CHECK_ID;
    private String F_EVALUATION;
    private String F_PROC_NAME;
    private String F_RETURN_REASON;
    private String REF_ID_;
    private String F_DIVIDE_ID;
    private String F_TIT_NAME;
    private String taskNodeId;
    private String F_LOCATION;
    private String F_TX_ID;
    @NonNull
    private String ID_;
    private String F_DESC;
    private String F_PROC_CONTENT;
    private int F_STATUS;
    private String F_RES_NAME;
    private String assigneeId;
    private String F_CHECK_NAME;
    private String F_RES_ID;
    private String F_DIVIDE_NAME;
    private String F_TIT_ID;
    private String F_BEF_PIC;
    private String F_PROC_ID;
    private Long createTime;
    private String F_TX_NAME;
    private String F_ORDER_NO;
    private String taskName;
    private String F_AFT_PIC;
    private String taskId;
    private String  F_CHECK_RESULT;
    private Long F_CREATE_TIME;
    private Long F_CHECK_DATE;
    private String F_PROC_DATE;
    private int F_EXT_STATUS;
    private int isReply;
    private int is_coming_timeout;
    @NonNull
    private String userId;

    @NonNull
    private int orderType;

    public int getIs_coming_timeout() {
        return is_coming_timeout;
    }

    public void setIs_coming_timeout(int is_coming_timeout) {
        this.is_coming_timeout = is_coming_timeout;
    }

    @NonNull
    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(@NonNull int orderType) {
        this.orderType = orderType;
    }

    public String getF_CHECK_CONTENT() {
        return F_CHECK_CONTENT;
    }

    public void setF_CHECK_CONTENT(String f_CHECK_CONTENT) {
        F_CHECK_CONTENT = f_CHECK_CONTENT;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getF_TYPE() {
        return F_TYPE;
    }

    public void setF_TYPE(String f_TYPE) {
        F_TYPE = f_TYPE;
    }

    public String getF_PROJECT_ID() {
        return F_PROJECT_ID;
    }

    public void setF_PROJECT_ID(String f_PROJECT_ID) {
        F_PROJECT_ID = f_PROJECT_ID;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getF_PROJECT_NAME() {
        return F_PROJECT_NAME;
    }

    public void setF_PROJECT_NAME(String f_PROJECT_NAME) {
        F_PROJECT_NAME = f_PROJECT_NAME;
    }

    public String getProInsId() {
        return proInsId;
    }

    public void setProInsId(String proInsId) {
        this.proInsId = proInsId;
    }

    public String getF_CHECK_ID() {
        return F_CHECK_ID;
    }

    public void setF_CHECK_ID(String f_CHECK_ID) {
        F_CHECK_ID = f_CHECK_ID;
    }

    public String getF_EVALUATION() {
        return F_EVALUATION;
    }

    public void setF_EVALUATION(String f_EVALUATION) {
        F_EVALUATION = f_EVALUATION;
    }

    public String getF_PROC_NAME() {
        return F_PROC_NAME;
    }

    public void setF_PROC_NAME(String f_PROC_NAME) {
        F_PROC_NAME = f_PROC_NAME;
    }

    public String getF_RETURN_REASON() {
        return F_RETURN_REASON;
    }

    public void setF_RETURN_REASON(String f_RETURN_REASON) {
        F_RETURN_REASON = f_RETURN_REASON;
    }

    @NonNull
    public String getREF_ID_() {
        return REF_ID_;
    }

    public void setREF_ID_(@NonNull String REF_ID_) {
        this.REF_ID_ = REF_ID_;
    }

    public String getF_DIVIDE_ID() {
        return F_DIVIDE_ID;
    }

    public void setF_DIVIDE_ID(String f_DIVIDE_ID) {
        F_DIVIDE_ID = f_DIVIDE_ID;
    }

    public String getF_TIT_NAME() {
        return F_TIT_NAME;
    }

    public void setF_TIT_NAME(String f_TIT_NAME) {
        F_TIT_NAME = f_TIT_NAME;
    }

    public String getTaskNodeId() {
        return taskNodeId;
    }

    public void setTaskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
    }

    public String getF_LOCATION() {
        return F_LOCATION;
    }

    public void setF_LOCATION(String f_LOCATION) {
        F_LOCATION = f_LOCATION;
    }

    public String getF_TX_ID() {
        return F_TX_ID;
    }

    public void setF_TX_ID(String f_TX_ID) {
        F_TX_ID = f_TX_ID;
    }

    public String getID_() {
        return ID_;
    }

    public void setID_(String ID_) {
        this.ID_ = ID_;
    }

    public String getF_DESC() {
        return F_DESC;
    }

    public void setF_DESC(String f_DESC) {
        F_DESC = f_DESC;
    }

    public String getF_PROC_CONTENT() {
        return F_PROC_CONTENT;
    }

    public void setF_PROC_CONTENT(String f_PROC_CONTENT) {
        F_PROC_CONTENT = f_PROC_CONTENT;
    }

    public int getF_STATUS() {
        return F_STATUS;
    }

    public void setF_STATUS(int f_STATUS) {
        F_STATUS = f_STATUS;
    }

    public String getF_RES_NAME() {
        return F_RES_NAME;
    }

    public void setF_RES_NAME(String f_RES_NAME) {
        F_RES_NAME = f_RES_NAME;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getF_CHECK_NAME() {
        return F_CHECK_NAME;
    }

    public void setF_CHECK_NAME(String f_CHECK_NAME) {
        F_CHECK_NAME = f_CHECK_NAME;
    }

    public String getF_RES_ID() {
        return F_RES_ID;
    }

    public void setF_RES_ID(String f_RES_ID) {
        F_RES_ID = f_RES_ID;
    }

    public String getF_DIVIDE_NAME() {
        return F_DIVIDE_NAME;
    }

    public void setF_DIVIDE_NAME(String f_DIVIDE_NAME) {
        F_DIVIDE_NAME = f_DIVIDE_NAME;
    }

    public String getF_TIT_ID() {
        return F_TIT_ID;
    }

    public void setF_TIT_ID(String f_TIT_ID) {
        F_TIT_ID = f_TIT_ID;
    }

    public String getF_BEF_PIC() {
        return F_BEF_PIC;
    }

    public void setF_BEF_PIC(String f_BEF_PIC) {
        F_BEF_PIC = f_BEF_PIC;
    }

    public String getF_PROC_ID() {
        return F_PROC_ID;
    }

    public void setF_PROC_ID(String f_PROC_ID) {
        F_PROC_ID = f_PROC_ID;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getF_TX_NAME() {
        return F_TX_NAME;
    }

    public void setF_TX_NAME(String f_TX_NAME) {
        F_TX_NAME = f_TX_NAME;
    }

    public String getF_ORDER_NO() {
        return F_ORDER_NO;
    }

    public void setF_ORDER_NO(String f_ORDER_NO) {
        F_ORDER_NO = f_ORDER_NO;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getF_AFT_PIC() {
        return F_AFT_PIC;
    }

    public void setF_AFT_PIC(String f_AFT_PIC) {
        F_AFT_PIC = f_AFT_PIC;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getF_CHECK_RESULT() {
        return F_CHECK_RESULT;
    }

    public void setF_CHECK_RESULT(String f_CHECK_RESULT) {
        F_CHECK_RESULT = f_CHECK_RESULT;
    }

    public Long getF_CREATE_TIME() {
        return F_CREATE_TIME;
    }

    public void setF_CREATE_TIME(Long f_CREATE_TIME) {
        F_CREATE_TIME = f_CREATE_TIME;
    }

    public Long getF_CHECK_DATE() {
        return F_CHECK_DATE;
    }

    public void setF_CHECK_DATE(Long f_CHECK_DATE) {
        F_CHECK_DATE = f_CHECK_DATE;
    }

    public String getF_PROC_DATE() {
        return F_PROC_DATE;
    }

    public void setF_PROC_DATE(String f_PROC_DATE) {
        F_PROC_DATE = f_PROC_DATE;
    }

    public int getF_EXT_STATUS() {
        return F_EXT_STATUS;
    }

    public void setF_EXT_STATUS(int f_EXT_STATUS) {
        F_EXT_STATUS = f_EXT_STATUS;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}
