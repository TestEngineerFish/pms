package com.einyun.app.pms.approval.model;

import java.io.Serializable;

public class ApprovalSumitBean implements Serializable {
//    {
//        "id": "65024227534703621",
//            "innerAuditHandleParam": {
//                "actionName": "approve",
//                "auditInsId": "65024247935795205",
//                "auditTaskId": "65351753150760965",
//                "comment": ""
//    }
//    }
    private String id;
    private String extensionDays;
    private String extensionApplicationId;
    private String applyTaskId;
    private String auditInsId;
    private String auditTaskId;
    private String comment;
    private String actionName;
    private InnerAuditHandleParam innerAuditHandleParam;

    public String getAuditTaskId() {
        return auditTaskId;
    }

    public void setAuditTaskId(String auditTaskId) {
        this.auditTaskId = auditTaskId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public String getExtensionDays() {
        return extensionDays;
    }

    public void setExtensionDays(String extensionDays) {
        this.extensionDays = extensionDays;
    }

    public String getExtensionApplicationId() {
        return extensionApplicationId;
    }

    public void setExtensionApplicationId(String extensionApplicationId) {
        this.extensionApplicationId = extensionApplicationId;
    }

    public String getApplyTaskId() {
        return applyTaskId;
    }

    public void setApplyTaskId(String applyTaskId) {
        this.applyTaskId = applyTaskId;
    }

    public InnerAuditHandleParam getInnerAuditHandleParam() {
        return innerAuditHandleParam;
    }

    public void setInnerAuditHandleParam(InnerAuditHandleParam innerAuditHandleParam) {
        this.innerAuditHandleParam = innerAuditHandleParam;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuditInsId() {
        return auditInsId;
    }

    public void setAuditInsId(String auditInsId) {
        this.auditInsId = auditInsId;
    }

    class InnerAuditHandleParam implements Serializable{
        private String actionName;
        private String auditInsId;
        private String auditTaskId;
        private String comment;

        public String getAuditInsId() {
            return auditInsId;
        }

        public void setAuditInsId(String auditInsId) {
            this.auditInsId = auditInsId;
        }

        public String getAuditTaskId() {
            return auditTaskId;
        }

        public void setAuditTaskId(String auditTaskId) {
            this.auditTaskId = auditTaskId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }
    }

}
