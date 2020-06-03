package com.einyun.app.pms.main.core.model;

import java.io.Serializable;

public class ScanResItemModel implements Serializable {

    /**
     * orderType : 2
     * orderNo : ccssjyeq-GC-GD-20200116190082
     * workOrderType : plan_order
     * orderStatus : 4
     * lineId : 482907
     * lineName : 工程
     * principalName : 李淑杰
     * lineCode : engineering_classification
     * resName : 高压开关柜
     * postponeStatus : 0
     * orderOverTime : 1
     * divideName : ops-长城盛世家园二期
     * location : 地库
     * id : 69191871598979078
     * orderTitle : 超级管理员在2020-01-16发起资源计划工单
     * deadline : 1581854351000
     * workflowId : 69191871598978054
     * createDate : 1579175951000
     */

    private String orderType;
    private String orderNo;
    private String workOrderType;
    private String orderStatus;
    private String lineId;
    private String lineName;
    private String principalName;
    private String lineCode;
    private String resName;
    private int postponeStatus;
    private int orderOverTime;
    private String divideName;
    private String location;
    private String taskId;
    private String taskNodeId;
    private String id;
    private String orderTitle;
    private String planName;
    private long deadline;
    private String workflowId;
    private String PROC_INST_ID_;
    private String F_inspection_work_plan_name;
    private long createDate;
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getWorkOrderType() {
        return workOrderType==null?"":workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getPostponeStatus() {
        return postponeStatus;
    }

    public void setPostponeStatus(int postponeStatus) {
        this.postponeStatus = postponeStatus;
    }

    public int getOrderOverTime() {
        return orderOverTime;
    }

    public void setOrderOverTime(int orderOverTime) {
        this.orderOverTime = orderOverTime;
    }

    public String getDivideName() {
        return divideName;
    }

    public void setDivideName(String divideName) {
        this.divideName = divideName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getF_inspection_work_plan_name() {
        return F_inspection_work_plan_name;
    }

    public void setF_inspection_work_plan_name(String f_inspection_work_plan_name) {
        F_inspection_work_plan_name = f_inspection_work_plan_name;
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

    public String getPROC_INST_ID_() {
        return PROC_INST_ID_;
    }

    public void setPROC_INST_ID_(String PROC_INST_ID_) {
        this.PROC_INST_ID_ = PROC_INST_ID_;
    }

    public String getPlanName() {
        return planName==null?"":planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
