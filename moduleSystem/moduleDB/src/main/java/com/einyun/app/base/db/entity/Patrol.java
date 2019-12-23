package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patrol_digests", primaryKeys = {"ID_", "userId", "listType"})
public class Patrol {
    /**
     * proInsId : 485732
     * orderType : 2
     * createTime : 1552703729000
     * ID_ : 41210
     * subject : APP测试账号3在2019-03-16发起资源巡查工单
     * F_plan_work_order_code :
     * taskName : 用户任务2
     * ownerId : 33012
     * F_inspection_work_plan_name : 恶趣味
     * assigneeId : 33012
     * taskId : 485753
     * taskNodeId : UserTask2
     */
    private int orderType;
    @NonNull
    private String userId;
    @NonNull
    private int listType;
    private int F_plan_work_order_state;
    private String F_line_name;
    @NonNull
    private String ID_;
    private String subject;
    private long F_creation_date;
    private String F_line_code;
    private String F_type_id;
    private String auditor_;
    private String F_type_name;
    private String proInsId;
    private String parentInstId;
    private long createTime;
    private String F_principal_name;
    private String F_plan_work_order_code;
    private String F_inspection_work_plan_name;
    private String auditor_name_;
    private String taskNodeId;
    private boolean isCached;
    private String taskId;
    private String assigneeId;
    private String ownerId;
    private String F_patrol_line_id;
    private int is_coming_timeout;

    public int getIs_coming_timeout() {
        return is_coming_timeout;
    }

    public void setIs_coming_timeout(int is_coming_timeout) {
        this.is_coming_timeout = is_coming_timeout;
    }

    public String getF_patrol_line_id() {
        return F_patrol_line_id;
    }

    public void setF_patrol_line_id(String f_patrol_line_id) {
        F_patrol_line_id = f_patrol_line_id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public int getListType() {
        return listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public int getF_plan_work_order_state() {
        return F_plan_work_order_state;
    }

    public void setF_plan_work_order_state(int f_plan_work_order_state) {
        F_plan_work_order_state = f_plan_work_order_state;
    }

    public String getF_line_name() {
        return F_line_name;
    }

    public void setF_line_name(String f_line_name) {
        F_line_name = f_line_name;
    }

    @NonNull
    public String getID_() {
        return ID_;
    }

    public void setID_(@NonNull String ID_) {
        this.ID_ = ID_;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getF_creation_date() {
        return F_creation_date;
    }

    public void setF_creation_date(long f_creation_date) {
        F_creation_date = f_creation_date;
    }

    public String getF_line_code() {
        return F_line_code;
    }

    public void setF_line_code(String f_line_code) {
        F_line_code = f_line_code;
    }

    public String getF_type_id() {
        return F_type_id;
    }

    public void setF_type_id(String f_type_id) {
        F_type_id = f_type_id;
    }

    public String getAuditor_() {
        return auditor_;
    }

    public void setAuditor_(String auditor_) {
        this.auditor_ = auditor_;
    }

    public String getF_type_name() {
        return F_type_name;
    }

    public void setF_type_name(String f_type_name) {
        F_type_name = f_type_name;
    }

    public String getProInsId() {
        return proInsId;
    }

    public void setProInsId(String proInsId) {
        this.proInsId = proInsId;
    }

    public String getParentInstId() {
        return parentInstId;
    }

    public void setParentInstId(String parentInstId) {
        this.parentInstId = parentInstId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getF_principal_name() {
        return F_principal_name;
    }

    public void setF_principal_name(String f_principal_name) {
        F_principal_name = f_principal_name;
    }

    public String getF_plan_work_order_code() {
        return F_plan_work_order_code;
    }

    public void setF_plan_work_order_code(String f_plan_work_order_code) {
        F_plan_work_order_code = f_plan_work_order_code;
    }

    public String getF_inspection_work_plan_name() {
        return F_inspection_work_plan_name;
    }

    public void setF_inspection_work_plan_name(String f_inspection_work_plan_name) {
        F_inspection_work_plan_name = f_inspection_work_plan_name;
    }

    public String getAuditor_name_() {
        return auditor_name_;
    }

    public void setAuditor_name_(String auditor_name_) {
        this.auditor_name_ = auditor_name_;
    }

    public String getTaskNodeId() {
        return taskNodeId;
    }

    public void setTaskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
    }
}
