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
        private String response_result;
        private String pd_remark;
        private String pd_assignor;
        private String pd_assignor_id;

        public String getHandle_cont() {
            return handle_cont;
        }

        public void setHandle_cont(String handle_cont) {
            this.handle_cont = handle_cont;
        }


        public String getResponse_result() {
            return response_result;
        }

        public void setResponse_result(String response_result) {
            this.response_result = response_result;
        }



        public String getPd_assignor() {
            return pd_assignor;
        }

        public void setPd_assignor(String pd_assignor) {
            this.pd_assignor = pd_assignor;
        }

        public String getPd_assignor_id() {
            return pd_assignor_id;
        }

        public void setPd_assignor_id(String pd_assignor_id) {
            this.pd_assignor_id = pd_assignor_id;
        }

        public String getPd_remark() {
            return pd_remark;
        }

        public void setPd_remark(String pd_remark) {
            this.pd_remark = pd_remark;
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
