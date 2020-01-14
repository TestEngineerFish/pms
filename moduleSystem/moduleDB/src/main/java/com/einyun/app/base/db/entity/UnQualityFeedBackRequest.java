package com.einyun.app.base.db.entity;

public class UnQualityFeedBackRequest {
    private BizDataBean bizData;
    private DoNextParam doNextParam;
    public UnQualityFeedBackRequest(){
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
        private String feedback_enclosure;
        private String reason;
        private String corrective_action;
        private String feedback_date;
        public String getFeedback_enclosure() {
            return feedback_enclosure;
        }

        public void setFeedback_enclosure(String feedback_enclosure) {
            this.feedback_enclosure = feedback_enclosure;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCorrective_action() {
            return corrective_action;
        }

        public void setCorrective_action(String corrective_action) {
            this.corrective_action = corrective_action;
        }

        public String getFeedback_date() {
            return feedback_date;
        }

        public void setFeedback_date(String feedback_date) {
            this.feedback_date = feedback_date;
        }
    }

    public static class DoNextParam {
        private String taskId;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }
}
