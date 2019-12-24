package com.einyun.app.library.resource.workorder.net.request;

public class ApplyCusCloseRequest {

    /**
     * bizData : {"fclose_apply_attach":"sss","fclose_apply_reason":"闭单原因 *:问询"}
     * doNextParam : {"taskId":"66464581324659716"}
     */

    private BizDataBean bizData;
    private DoNextParamBean doNextParam;

    public ApplyCusCloseRequest(BizDataBean bizData, DoNextParamBean doNextParam) {
        this.bizData = bizData;
        this.doNextParam = doNextParam;
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
         * fclose_apply_attach : sss
         * fclose_apply_reason : 闭单原因 *:问询
         */

        private String fclose_apply_attach;
        private String fclose_apply_reason;

        public String getFclose_apply_attach() {
            return fclose_apply_attach;
        }

        public void setFclose_apply_attach(String fclose_apply_attach) {
            this.fclose_apply_attach = fclose_apply_attach;
        }

        public String getFclose_apply_reason() {
            return fclose_apply_reason;
        }

        public void setFclose_apply_reason(String fclose_apply_reason) {
            this.fclose_apply_reason = fclose_apply_reason;
        }
    }

    public static class DoNextParamBean {
        /**
         * taskId : 66464581324659716
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
