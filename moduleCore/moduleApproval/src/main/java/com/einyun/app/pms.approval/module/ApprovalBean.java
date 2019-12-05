package com.einyun.app.pms.approval.module;

import com.einyun.app.base.paging.bean.PageBean;

import java.util.List;

public class ApprovalBean {
    /**
     * pageBean : {"page":1,"pageSize":10,"showTotal":true}
     * querys : [{"operation":"EQUAL","property":"audit_type","relation":"AND","value":"INNER_AUDIT_CREATE_PLAN"},{"operation":"EQUAL","property":"audit_sub_type","relation":"AND","value":"CREATE_WORK_PLAN"},{"operation":"EQUAL","property":"vo.status","relation":"AND","value":"approve"}]
     */

    private PageBean pageBean;
    private List<QuerysBean> querys;

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public List<QuerysBean> getQuerys() {
        return querys;
    }

    public void setQuerys(List<QuerysBean> querys) {
        this.querys = querys;
    }

    public static class PageBeanBean {
        /**
         * page : 1
         * pageSize : 10
         * showTotal : true
         */

        private int page;
        private int pageSize;
        private boolean showTotal;

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

        public boolean isShowTotal() {
            return showTotal;
        }

        public void setShowTotal(boolean showTotal) {
            this.showTotal = showTotal;
        }
    }

    public static class QuerysBean {
        /**
         * operation : EQUAL
         * property : audit_type
         * relation : AND
         * value : INNER_AUDIT_CREATE_PLAN
         */

        private String operation;
        private String property;
        private String relation;
        private String value;

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
