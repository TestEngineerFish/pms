package com.einyun.app.pms.customerinquiries.model;

public class DealRequest {

    /**
     * bizData : {"handle_cont":"回复内容 *:提交"}
     * doNextParam : {"taskId":"66465331870194692"}
     */
    public DealRequest(){
        this.bizData=new BizDataBean();
        this.doNextParam=new DoNextParamBean();
    }
    private BizDataBean bizData;
    private DoNextParamBean doNextParam;

    public BizDataBean getBizData() {
        return bizData;
    }

    public void setBizData(BizDataBean bizData) {
        this.bizData = bizData;
    }

    public DoNextParamBean getDoNextParam() {
        return doNextParam;
    }

    public void setDoNextParam(DoNextParamBean doNextParam) {
        this.doNextParam = doNextParam;
    }

    public static class BizDataBean {
        /**
         * handle_cont : 回复内容 *:提交
         */

        private String handle_cont;

        public String getHandle_cont() {
            return handle_cont;
        }

        public void setHandle_cont(String handle_cont) {
            this.handle_cont = handle_cont;
        }
    }

    public static class DoNextParamBean {
        /**
         * taskId : 66465331870194692
         */

        private String taskId;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }
}
