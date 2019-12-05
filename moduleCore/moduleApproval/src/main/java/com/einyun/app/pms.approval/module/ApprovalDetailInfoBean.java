package com.einyun.app.pms.approval.module;

import java.util.List;

public class ApprovalDetailInfoBean {

    /**
     * rows : [{"approvalRole":"测试用菜单","audit_id":"c98e505454314a5ebdff7b7d678d9028","ID_":"6d5607b8ae52401a8ef8092ba9304a7f","auditor":"pick001","audit_date":1557818640000,"statusName":"已通过","comment":"通过了啊","auditor_id":"91f46292a8c743b28e11b93bd258c7ef","status":"approve"}]
     * total : 1
     * page : 0
     * pageSize : 0
     */

    private int total;
    private int page;
    private int pageSize;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * approvalRole : 测试用菜单
         * audit_id : c98e505454314a5ebdff7b7d678d9028
         * ID_ : 6d5607b8ae52401a8ef8092ba9304a7f
         * auditor : pick001
         * audit_date : 1557818640000
         * statusName : 已通过
         * comment : 通过了啊
         * auditor_id : 91f46292a8c743b28e11b93bd258c7ef
         * status : approve
         */

        private String approvalRole;
        private String audit_id;
        private String ID_;
        private String auditor;
        private long audit_date;
        private String statusName;
        private String comment;
        private String auditor_id;
        private String status;

        public String getApprovalRole() {
            return approvalRole;
        }

        public void setApprovalRole(String approvalRole) {
            this.approvalRole = approvalRole;
        }

        public String getAudit_id() {
            return audit_id;
        }

        public void setAudit_id(String audit_id) {
            this.audit_id = audit_id;
        }

        public String getID_() {
            return ID_;
        }

        public void setID_(String ID_) {
            this.ID_ = ID_;
        }

        public String getAuditor() {
            return auditor;
        }

        public void setAuditor(String auditor) {
            this.auditor = auditor;
        }

        public long getAudit_date() {
            return audit_date;
        }

        public void setAudit_date(long audit_date) {
            this.audit_date = audit_date;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getAuditor_id() {
            return auditor_id;
        }

        public void setAuditor_id(String auditor_id) {
            this.auditor_id = auditor_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
