package com.einyun.app.pms.disqualified.net.request;



import com.einyun.app.base.paging.bean.PageBean;

import java.util.List;

public class DisqualifiedListRequest {

    /**
     * pageBean : {"page":1,"pageSize":10,"showTotal":false}
     * querys : [{"property":"wx_dk_id","operation":"EQUAL","value":"divideId","relation":"AND"},{"property":"wx_cate_id","operation":"EQUAL","value":"cate","relation":"AND"},{"property":"state","operation":"EQUAL","value":"state","relation":"AND"}]
     * sorter : [{"property":"wx_time","direction":"DESC"}]
     */

    private PageBean pageBean;
    private List<QuerysBean> querys;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    //    private List<SorterBean> sorter;
  private String searchValue;
    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPage(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public List<QuerysBean> getQuerys() {
        return querys;
    }

    public void setQuerys(List<QuerysBean> querys) {
        this.querys = querys;
    }

//    public List<SorterBean> getSorter() {
//        return sorter;
//    }
//
//    public void setSorter(List<SorterBean> sorter) {
//        this.sorter = sorter;
//    }

    public static class PageBeanBean {
        /**
         * page : 1
         * pageSize : 10
         * showTotal : false
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
         * property : wx_dk_id
         * operation : EQUAL
         * value : divideId
         * relation : AND
         */

        private String property;
        private String operation;
        private String value;
        private String relation;

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
    }

    public static class SorterBean {
        /**
         * property : wx_time
         * direction : DESC
         */

        private String property;
        private String direction;

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }
}
