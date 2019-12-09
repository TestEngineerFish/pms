package com.einyun.app.pms.approval.module;

import java.io.Serializable;

public class ApprovalItemmodule implements Serializable {
    /**
     * approvalRole : 工程服务单元管理中心员工
     * divide_name : 测试苏南地块
     * ID_ : 50ff4640252e4ed783baa25d54c93607
     * subject : 工程员工在2019-04-24发起报修工单强制闭单申请
     * audit_code : QZBD20190424100003
     * apply_user : 工程员工
     * audit_sub_type : FORCE_CLOSE_REPAIR
     * ownerId : aa984e945b844a1a85b0a9d144be3d78
     * assigneeId : aa984e945b844a1a85b0a9d144be3d78
     * proInsId : bd881727bb534d1b9e1beb1d169373ea
     * divide_id : 0e4a9d06cd3741f088d6c0958ef02f7e
     * apply_date : 1556072124000
     * form_data : {"code":"SFSYSFQ001-GC-BX-20190412200006","workOrderCategory":"客户报修工单","applyReason":"好","category":"工程维修类","repairArea":"公区"}
     * apply_user_id : aa984e945b844a1a85b0a9d144be3d78
     * parentInstId : 0
     * createTime : 1556072125000
     * apply_flow_key : customer_repair_flow
     * taskName : 报修强制闭单审批
     * apply_instance_id : cc713e6692d34327a207dfba021f16dd
     * apply_key_title : SFSYSFQ001-GC-BX-20190412200006
     * taskId : a0ebf6aec3924f17b3821e82190ddf63
     * taskNodeId : RepairFclose
     * audit_type : INNER_AUDIT_FORCE_CLOSE
     * status : 提交审批
     */

    private String approvalRole;
    private String divide_name;
    private String ID_;
    private String subject;
    private String audit_code;
    private String apply_user;
    private String audit_sub_type;
    private String ownerId;
    private String assigneeId;
    private String proInsId;
    private String divide_id;
    private long apply_date;
    private long audit_date;
    private String form_data;
    private String apply_user_id;
    private String parentInstId;
    private long createTime;
    private String apply_flow_key;
    private String taskName;
    private String apply_instance_id;
    private String apply_key_title;
    private String taskId;
    private String taskNodeId;
    private String audit_type;
    private String status;

    public long getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(long audit_date) {
        this.audit_date = audit_date;
    }

    public String getApprovalRole() {
        return approvalRole;
    }

    public void setApprovalRole(String approvalRole) {
        this.approvalRole = approvalRole;
    }

    public String getDivide_name() {
        return divide_name;
    }

    public void setDivide_name(String divide_name) {
        this.divide_name = divide_name;
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

    public String getAudit_code() {
        return audit_code;
    }

    public void setAudit_code(String audit_code) {
        this.audit_code = audit_code;
    }

    public String getApply_user() {
        return apply_user;
    }

    public void setApply_user(String apply_user) {
        this.apply_user = apply_user;
    }

    public String getAudit_sub_type() {
        return audit_sub_type;
    }

    public void setAudit_sub_type(String audit_sub_type) {
        this.audit_sub_type = audit_sub_type;
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

    public String getProInsId() {
        return proInsId;
    }

    public void setProInsId(String proInsId) {
        this.proInsId = proInsId;
    }

    public String getDivide_id() {
        return divide_id;
    }

    public void setDivide_id(String divide_id) {
        this.divide_id = divide_id;
    }

    public long getApply_date() {
        return apply_date;
    }

    public void setApply_date(long apply_date) {
        this.apply_date = apply_date;
    }

    public String getForm_data() {
        return form_data;
    }

    public void setForm_data(String form_data) {
        this.form_data = form_data;
    }

    public String getApply_user_id() {
        return apply_user_id;
    }

    public void setApply_user_id(String apply_user_id) {
        this.apply_user_id = apply_user_id;
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

    public String getApply_flow_key() {
        return apply_flow_key;
    }

    public void setApply_flow_key(String apply_flow_key) {
        this.apply_flow_key = apply_flow_key;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getApply_instance_id() {
        return apply_instance_id;
    }

    public void setApply_instance_id(String apply_instance_id) {
        this.apply_instance_id = apply_instance_id;
    }

    public String getApply_key_title() {
        return apply_key_title;
    }

    public void setApply_key_title(String apply_key_title) {
        this.apply_key_title = apply_key_title;
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

    public String getAudit_type() {
        return audit_type;
    }

    public void setAudit_type(String audit_type) {
        this.audit_type = audit_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
