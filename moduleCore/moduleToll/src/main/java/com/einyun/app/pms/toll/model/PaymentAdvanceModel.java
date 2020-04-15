package com.einyun.app.pms.toll.model;

import java.util.List;

public class PaymentAdvanceModel {

    /**
     * code : 0
     * data : [{"feeAttribute":"4","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b","feeItemName":"物业费","isInvoice":"0","nationalCode":"3040801010000000000","productCode":"001","taxRate":"0.0600"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * feeAttribute : 4
         * feeItemId : 39b5c285-f53e-4c67-a15d-7bf7ba865c4b
         * feeItemName : 物业费
         * isInvoice : 0
         * nationalCode : 3040801010000000000
         * productCode : 001
         * taxRate : 0.0600
         * unitPrice:月单价
         * residueAmount:剩余可用金额
         */

        private String feeAttribute;
        private String feeItemId;
        private String feeItemName;
        private String isInvoice;
        private String nationalCode;
        private String productCode;
        private String taxRate;
        private String unitPrice;
        private String parkingNum;
        private String parkingId;
        private String parkingName;
        private String residueAmount;
        private boolean isCheck;

        public String getFeeAttribute() {
            return feeAttribute;
        }

        public void setFeeAttribute(String feeAttribute) {
            this.feeAttribute = feeAttribute;
        }

        public String getFeeItemId() {
            return feeItemId;
        }

        public void setFeeItemId(String feeItemId) {
            this.feeItemId = feeItemId;
        }

        public String getFeeItemName() {
            return feeItemName;
        }

        public void setFeeItemName(String feeItemName) {
            this.feeItemName = feeItemName;
        }

        public String getIsInvoice() {
            return isInvoice;
        }

        public void setIsInvoice(String isInvoice) {
            this.isInvoice = isInvoice;
        }

        public String getNationalCode() {
            return nationalCode;
        }

        public void setNationalCode(String nationalCode) {
            this.nationalCode = nationalCode;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getParkingNum() {
            return parkingNum;
        }

        public void setParkingNum(String parkingNum) {
            this.parkingNum = parkingNum;
        }

        public String getParkingId() {
            return parkingId;
        }

        public void setParkingId(String parkingId) {
            this.parkingId = parkingId;
        }

        public String getParkingName() {
            return parkingName;
        }

        public void setParkingName(String parkingName) {
            this.parkingName = parkingName;
        }

        public String getResidueAmount() {
            return residueAmount;
        }

        public void setResidueAmount(String residueAmount) {
            this.residueAmount = residueAmount;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
}
