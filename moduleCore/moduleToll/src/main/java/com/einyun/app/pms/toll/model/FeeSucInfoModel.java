package com.einyun.app.pms.toll.model;

import java.math.BigDecimal;
import java.util.List;

public class FeeSucInfoModel {

    /**
     * code : 0
     * data : {"paymentList":[{"costDateCourse":"201701","feeItemCode":"2001","feeItemName":"公共水电费","paidMoney":"500.00","payTime":"2019 - 11 - 12 13: 15: 00","receivableId":"10001"}]}
     * msg :
     */

    private String code;
    private DataBean data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        private List<PaymentListBean> paymentList;

        public List<PaymentListBean> getPaymentList() {
            return paymentList;
        }

        public void setPaymentList(List<PaymentListBean> paymentList) {
            this.paymentList = paymentList;
        }

        public static class PaymentListBean {
            /**
             * costDateCourse : 201701
             * feeItemCode : 2001
             * feeItemName : 公共水电费
             * paidMoney : 500.00
             * payTime : 2019 - 11 - 12 13: 15: 00
             * receivableId : 10001
             */

            private String costDateCourse;
            private String feeItemCode;
            private String feeItemName;
            private BigDecimal paidMoney;
            private long paytime;
            private String receivableId;

            public String getCostDateCourse() {
                return costDateCourse;
            }

            public void setCostDateCourse(String costDateCourse) {
                this.costDateCourse = costDateCourse;
            }

            public String getFeeItemCode() {
                return feeItemCode;
            }

            public void setFeeItemCode(String feeItemCode) {
                this.feeItemCode = feeItemCode;
            }

            public String getFeeItemName() {
                return feeItemName;
            }

            public void setFeeItemName(String feeItemName) {
                this.feeItemName = feeItemName;
            }

            public BigDecimal getPaidMoney() {
                return paidMoney;
            }

            public void setPaidMoney(BigDecimal paidMoney) {
                this.paidMoney = paidMoney;
            }

            public long getPayTime() {
                return paytime;
            }

            public void setPayTime(long payTime) {
                this.paytime = payTime;
            }

            public String getReceivableId() {
                return receivableId;
            }

            public void setReceivableId(String receivableId) {
                this.receivableId = receivableId;
            }
        }
    }
}
