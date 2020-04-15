package com.einyun.app.pms.toll.model;

public class WorthTimeModel {

    /**
     * code : 0
     * data : {"remark":"尊敬的18-01-01-18-01-2401业主您好，您的物业相关费用有欠费，欠费总额1682.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:09:22"}
     * msg :
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * remark : 尊敬的18-01-01-18-01-2401业主您好，您的物业相关费用有欠费，欠费总额1682.6400元，烦请您尽快缴纳相关费用，感谢您的支持
         * urgeDate : 2020-02-26 18:09:22
         */

        private String remark;
        private String urgeDate;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUrgeDate() {
            return urgeDate;
        }

        public void setUrgeDate(String urgeDate) {
            this.urgeDate = urgeDate;
        }
    }
}
