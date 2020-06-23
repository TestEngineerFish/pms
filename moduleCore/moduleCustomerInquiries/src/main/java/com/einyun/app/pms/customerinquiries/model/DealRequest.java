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
        private String assign_grab_user;
        private String assign_grab_user_id;
        private String response_result;
        private String pd_remake;

        public String getHandle_cont() {
            return handle_cont;
        }

        public void setHandle_cont(String handle_cont) {
            this.handle_cont = handle_cont;
        }

        public String getAssign_grab_user() {
            return assign_grab_user;
        }

        public void setAssign_grab_user(String assign_grab_user) {
            this.assign_grab_user = assign_grab_user;
        }

        public String getAssign_grab_user_id() {
            return assign_grab_user_id;
        }

        public void setAssign_grab_user_id(String assign_grab_user_id) {
            this.assign_grab_user_id = assign_grab_user_id;
        }

        public String getResponse_result() {
            return response_result;
        }

        public void setResponse_result(String response_result) {
            this.response_result = response_result;
        }

        public String getPd_remake() {
            return pd_remake;
        }

        public void setPd_remake(String pd_remake) {
            this.pd_remake = pd_remake;
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
