package com.einyun.app.pms.toll.model;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrderRequest {

    /**
     * payOrderType : pty-101
     * userId : 123456
     * payOrderId : 789456
     * payAmount : 0.01
     * houseId : ops-035B19DF-F8F7-4898-A6C0-20F9DBAEF560
     * houseName : test
     * divideId : 7
     * paymentDetails : [{"chargeTypeCode":"3040801010000000000","chargeReceivableId":"02a6570f-b175-46de-9d91-604f7d6ab5f4","chargeAmount":0.01}]
     */

    private String payOrderType;
    private String userId;
    private String payOrderId;
    private double payAmount;
    private String houseId;
    private String houseName;
    private String divideId;
    private List<PaymentDetailsBean> paymentDetails;

    public String getPayOrderType() {
        return payOrderType;
    }

    public void setPayOrderType(String payOrderType) {
        this.payOrderType = payOrderType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getDivideId() {
        return divideId;
    }

    public void setDivideId(String divideId) {
        this.divideId = divideId;
    }

    public List<PaymentDetailsBean> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentDetailsBean> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public static class PaymentDetailsBean {
        /**
         * chargeTypeCode : 3040801010000000000
         * chargeReceivableId : 02a6570f-b175-46de-9d91-604f7d6ab5f4
         * chargeAmount : 0.01
         */

        private String chargeTypeCode;
        private String chargeReceivableId;
        private BigDecimal chargeAmount;

        public String getChargeTypeCode() {
            return chargeTypeCode;
        }

        public void setChargeTypeCode(String chargeTypeCode) {
            this.chargeTypeCode = chargeTypeCode;
        }

        public String getChargeReceivableId() {
            return chargeReceivableId;
        }

        public void setChargeReceivableId(String chargeReceivableId) {
            this.chargeReceivableId = chargeReceivableId;
        }

        public BigDecimal getChargeAmount() {
            return chargeAmount;
        }

        public void setChargeAmount(BigDecimal chargeAmount) {
            this.chargeAmount = chargeAmount;
        }
    }
}
