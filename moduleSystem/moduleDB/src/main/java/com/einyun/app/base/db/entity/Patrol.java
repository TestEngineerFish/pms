package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patrol_digests")
public class Patrol{
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
    private String proInsId;
    private int orderType;
    private long createTime;
    private long F_creation_date;
    private int F_plan_work_order_state;
    private String userId;
    @PrimaryKey
    @NonNull
    private String ID_;
    private String subject;
    private String F_house_code;
    private String F_grid_name;
    private String F_building_name;
    private String F_unit_name;
    private String F_floor;
    private String F_plan_work_order_code;
    private String taskName;
    private String ownerId;
    private String F_inspection_work_plan_name;
    private String assigneeId;
    private String taskId;
    private String taskNodeId;
    private long F_actual_completion_time;
    private boolean isCached;
    private boolean isWarn;

    private String F_type_name; //秩序巡更点巡查，环境巡更点巡查，客服巡更点巡查
    private String F_type_id;  // inspection_group

    public String getF_type_name() {
        return F_type_name;
    }

    public void setF_type_name(String f_type_name) {
        F_type_name = f_type_name;
    }

    public String getF_type_id() {
        return F_type_id;
    }

    public void setF_type_id(String f_type_id) {
        F_type_id = f_type_id;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }

    public String getF_house_code() {
        return F_house_code;
    }

    public void setF_house_code(String f_house_code) {
        F_house_code = f_house_code;
    }

    public String getF_grid_name() {
        return F_grid_name;
    }

    public void setF_grid_name(String f_grid_name) {
        F_grid_name = f_grid_name;
    }

    public String getF_building_name() {
        return F_building_name;
    }

    public void setF_building_name(String f_building_name) {
        F_building_name = f_building_name;
    }

    public String getF_unit_name() {
        return F_unit_name;
    }

    public void setF_unit_name(String f_unit_name) {
        F_unit_name = f_unit_name;
    }

    public String getF_floor() {
        return F_floor;
    }

    public void setF_floor(String f_floor) {
        F_floor = f_floor;
    }

    public int getF_plan_work_order_state() {
        return F_plan_work_order_state;
    }

    public void setF_plan_work_order_state(int f_plan_work_order_state) {
        F_plan_work_order_state = f_plan_work_order_state;
    }

    public long getF_actual_completion_time() {
        return F_actual_completion_time;
    }

    public void setF_actual_completion_time(long f_actual_completion_time) {
        F_actual_completion_time = f_actual_completion_time;
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

    public long getF_creation_date() {
        return F_creation_date;
    }

    public void setF_creation_date(long f_creation_date) {
        F_creation_date = f_creation_date;
    }

    public String getID_() {
        return ID_;
    }

    public void setID_(String ID_) {
        this.ID_ = ID_;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getF_plan_work_order_code() {
        return F_plan_work_order_code;
    }

    public void setF_plan_work_order_code(String F_plan_work_order_code) {
        this.F_plan_work_order_code = F_plan_work_order_code;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getF_inspection_work_plan_name() {
        return F_inspection_work_plan_name;
    }

    public void setF_inspection_work_plan_name(String F_inspection_work_plan_name) {
        this.F_inspection_work_plan_name = F_inspection_work_plan_name;
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

    public boolean isWarn() {
        return isWarn;
    }

    public void setWarn(boolean warn) {
        isWarn = warn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
