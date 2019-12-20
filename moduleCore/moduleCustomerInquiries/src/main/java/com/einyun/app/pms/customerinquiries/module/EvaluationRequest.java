package com.einyun.app.pms.customerinquiries.module;

public class EvaluationRequest {

    /**
     * bizData : {"c_is_solve":1,"handle_cont":"回复内容 *:提交"}
     * doNextParam : {"taskId":"66465544471054340"}
     */

    private BizDataBean bizData;
    private DoNextParamBean doNextParam;

    public EvaluationRequest() {
        this.bizData = new BizDataBean();
        this.doNextParam = new DoNextParamBean();
    }

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
         * c_is_solve : 1
         * handle_cont : 回复内容 *:提交
         */

        private int c_is_solve;
        private String handle_cont;

        public int getC_is_solve() {
            return c_is_solve;
        }

        public void setC_is_solve(int c_is_solve) {
            this.c_is_solve = c_is_solve;
        }

        public String getHandle_cont() {
            return handle_cont;
        }

        public void setHandle_cont(String handle_cont) {
            this.handle_cont = handle_cont;
        }
    }

    public static class DoNextParamBean {
        /**
         * taskId : 66465544471054340
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
