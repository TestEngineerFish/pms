package com.einyun.app.pms.toll.model;

import java.math.BigDecimal;
import java.util.List;

public class PaymentAdvanceModel2 {


    /**
     * code : 0
     * data : {"dataList":[{"feeIAttribute":"1","feeItemId":"040376be-5453-444f-b345-bc19619555ee","feeItemName":"水费"},{"feeIAttribute":"4","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b","feeItemName":"物业费","unitPrice":1},{"feeIAttribute":"5","feeItemId":"e3e8be03-39f6-4121-badc-cfbf841ea2bc","feeItemName":"电费"},{"feeIAttribute":"2","feeItemId":"e5559404-a6bb-473c-bc4a-1d6ccaa370b3","feeItemName":"产权车位服务费","parkingId":"6e05cf10-1887-47a9-bc26-6fbd733369e3","parkingName":"c313","parkingNum":"Ktest","unitPrice":1}]}
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
        private List<DataListBean> dataList;

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * feeIAttribute : 1
             * feeItemId : 040376be-5453-444f-b345-bc19619555ee
             * feeItemName : 水费
             * unitPrice : 1
             * parkingId : 6e05cf10-1887-47a9-bc26-6fbd733369e3
             * parkingName : c313
             * parkingNum : Ktest
             */

            private String feeIAttribute;
            private BigDecimal chargeAmount;
            private BigDecimal residueAmount;
            private String feeItemId;
            private String feeItemName;
            private BigDecimal unitPrice;
            private String parkingId;
            private String parkingName;
            private String parkingNum;
            private String clientName;
            private int months=1;
            private boolean isCheck=true;
            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }
            public String getFeeIAttribute() {
                return feeIAttribute;
            }

            public void setFeeIAttribute(String feeIAttribute) {
                this.feeIAttribute = feeIAttribute;
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

            public BigDecimal getUnitPrice() {
                return unitPrice==null?new BigDecimal("0.00"):unitPrice;
            }

            public void setUnitPrice(BigDecimal unitPrice) {
                this.unitPrice = unitPrice;
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

            public String getParkingNum() {
                return parkingNum;
            }

            public void setParkingNum(String parkingNum) {
                this.parkingNum = parkingNum;
            }

            public BigDecimal getResidueAmount() {
                return residueAmount==null?new BigDecimal("0"):residueAmount;
            }

            public void setResidueAmount(BigDecimal residueAmount) {
                this.residueAmount = residueAmount;
            }

            public BigDecimal getChargeAmount() {
                return chargeAmount==null?new BigDecimal("0.00"):chargeAmount;
            }

            public void setChargeAmount(BigDecimal chargeAmount) {
                this.chargeAmount = chargeAmount;
            }

            public String getClientName() {
                return clientName==null?"":clientName;
            }

            public void setClientName(String clientName) {
                this.clientName = clientName;
            }

            public int getMonths() {
                return months;
            }

            public void setMonths(int months) {
                this.months = months;
            }
        }
    }
}
