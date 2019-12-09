package com.einyun.app.pms.approval.module;

import java.io.Serializable;

public class ApprovalSumitBean implements Serializable {
//    {
//        "id": "65024227534703621",
//            "innerAuditHandleParam": {
//        "actionName": "approve",
//                "auditInsId": "65024247935795205",
//                "auditTaskId": "65351753150760965",
//                "comment": ""
//    }
//    }
    private String id;

    public InnerAuditHandleParam getInnerAuditHandleParam() {
        return innerAuditHandleParam;
    }

    public void setInnerAuditHandleParam(InnerAuditHandleParam innerAuditHandleParam) {
        this.innerAuditHandleParam = innerAuditHandleParam;
    }

    private InnerAuditHandleParam innerAuditHandleParam;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
