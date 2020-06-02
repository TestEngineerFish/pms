package com.einyun.app.pms.toll.model;

import java.util.List;

public class LackDetailModel {

    /**
     * code : 0
     * data : {"arrearsMonth":"202001","clientName":"郑敏","clientPhone":"15996377173","feeAmount":1289.64,"houseInnerName":"20-01-01-20-01-202","urgeList":[{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:51:29"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:51:27"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:48:48"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:48:44"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:48:41"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:09:22"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:07:18"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:00:04"}]}
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
         * arrearsMonth : 202001
         * clientName : 郑敏
         * clientPhone : 15996377173
         * feeAmount : 1289.64
         * houseInnerName : 20-01-01-20-01-202
         * urgeList : [{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:51:29"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:51:27"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:48:48"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:48:44"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:48:41"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:09:22"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:07:18"},{"remark":"尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持","urgeDate":"2020-02-26 18:00:04"}]
         */

        private String arrearsMonth;
        private String clientName;
        private String clientPhone;
        private String clientId;
        private double feeAmount;
        private String houseInnerName;
        private String houseId;
        private List<UrgeListBean> urgeList;

        public String getArrearsMonth() {
            return arrearsMonth;
        }

        public void setArrearsMonth(String arrearsMonth) {
            this.arrearsMonth = arrearsMonth;
        }

        public String getClientName() {
            return clientName==null?"":clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientPhone() {
            return clientPhone;
        }

        public void setClientPhone(String clientPhone) {
            this.clientPhone = clientPhone;
        }

        public double getFeeAmount() {
            return feeAmount;
        }

        public void setFeeAmount(double feeAmount) {
            this.feeAmount = feeAmount;
        }

        public String getHouseInnerName() {
            return houseInnerName;
        }

        public void setHouseInnerName(String houseInnerName) {
            this.houseInnerName = houseInnerName;
        }

        public List<UrgeListBean> getUrgeList() {
            return urgeList;
        }

        public void setUrgeList(List<UrgeListBean> urgeList) {
            this.urgeList = urgeList;
        }

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public String getClientId() {
            return clientId==null?"":clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public static class UrgeListBean {
            /**
             * remark : 尊敬的20-01-01-20-01-202业主您好，您的物业相关费用有欠费，欠费总额1289.6400元，烦请您尽快缴纳相关费用，感谢您的支持
             * urgeDate : 2020-02-26 18:51:29
             */

            private String remark;
            private String urgeDate;
            private int type;
            private String user;

            public String getRemark() {
                return remark==null?"":remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getUrgeDate() {
                return urgeDate==null?"":urgeDate;
            }

            public void setUrgeDate(String urgeDate) {
                this.urgeDate = urgeDate;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUser() {
                return user==null?"":user;
            }

            public void setUser(String user) {
                this.user = user;
            }
        }
    }
}

