package com.einyun.app.pms.disqualified.db;

public class UnQualityVerificationRequest {
    private BizDataBean bizData;
    private DoNextParam doNextParam;
    public UnQualityVerificationRequest(){
        this.bizData=new BizDataBean();
        this.doNextParam=new DoNextParam();
    }
    public BizDataBean getBizData() {
        return bizData;
    }

    public void setBizData(BizDataBean bizData) {
        this.bizData = bizData;
    }

    public DoNextParam getDoNextParamt() {
        return doNextParam;
    }

    public void setDoNextParam(DoNextParam doNextParam) {
        this.doNextParam = doNextParam;
    }

    public static class BizDataBean {
        public String getVerification_situation() {
            return verification_situation==null?"":verification_situation;
        }

        public void setVerification_situation(String verification_situation) {
            this.verification_situation = verification_situation;
        }

        public String getVerification_enclosure() {
            return verification_enclosure==null?"":verification_enclosure;
        }

        public void setVerification_enclosure(String verification_enclosure) {
            this.verification_enclosure = verification_enclosure;
        }

        public String getVerification_date() {
            return verification_date==null?"":verification_date;
        }

        public void setVerification_date(String verification_date) {
            this.verification_date = verification_date;
        }

        public int getIs_pass() {
            return is_pass;
        }

        public void setIs_pass(int is_pass) {
            this.is_pass = is_pass;
        }

        private String verification_situation;
        private String verification_enclosure;
        private String verification_date;
        private int is_pass;

    }

    public static class DoNextParam {
        private String taskId;

        public String getTaskId() {
            return taskId==null?"":taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }
}
