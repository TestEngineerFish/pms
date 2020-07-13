package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plans")
public class Plan {
    private String proInsId;
    private int orderType;
    private long createTime;
    private long F_CREATE_TIME;
    @NonNull
    private String ID_;
    private String F_ORDER_NO;
    private String taskName;
    private String F_WP_NAME;
    private String ownerId;
    private String assigneeId;
    private String taskId;
    private String taskNodeId;
    private String subject;
    private String F_STATUS;
    private int F_EXT_STATUS;
    private int F_OT_STATUS;
    private boolean isCached;
    @NonNull
    private String userId;
    @NonNull
    private int listType;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProInsId() {
        return proInsId;
    }

    public void setProInsId(String proInsId) {
        this.proInsId = proInsId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getF_CREATE_TIME() {
        return F_CREATE_TIME;
    }

    public void setF_CREATE_TIME(long f_CREATE_TIME) {
        F_CREATE_TIME = f_CREATE_TIME;
    }

    @NonNull
    public String getID_() {
        return ID_;
    }

    public void setID_( @NonNull String ID_) {
        this.ID_ = ID_;
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

    public String getF_WP_NAME() {
        return F_WP_NAME;
    }

    public void setF_WP_NAME(String f_WP_NAME) {
        F_WP_NAME = f_WP_NAME;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskNodeId() {
        return taskNodeId;
    }

    public void setTaskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getF_STATUS() {
        return F_STATUS;
    }

    public void setF_STATUS(String f_STATUS) {
        F_STATUS = f_STATUS;
    }

    public int getF_EXT_STATUS() {
        return F_EXT_STATUS;
    }

    public void setF_EXT_STATUS(int f_EXT_STATUS) {
        F_EXT_STATUS = f_EXT_STATUS;
    }

    public int getF_OT_STATUS() {
        return F_OT_STATUS;
    }

    public void setF_OT_STATUS(int f_OT_STATUS) {
        F_OT_STATUS = f_OT_STATUS;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public int getListType() {
        return listType;
    }

    public void setListType( @NonNull int listType) {
        this.listType = listType;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }
}
