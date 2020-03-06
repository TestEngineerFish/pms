package com.einyun.app.pms.toll.model;

import java.util.List;

public class LackListModel {
    private Value value;
    private long code;
    private String message;
    public static class Value{
        private List<PaymentList> paymentList;
        private long totalPage;
        private String houseId;
        public static class PaymentList{
            private boolean isCheckParent=false;
            private String feeItemCode;
            private String chargeTypeName;
            private List<ListBean> list;
            private String feeItemName;
            private String chargeTypeCode;

            public boolean isCheckParent() {
                return isCheckParent;
            }

            public void setCheckParent(boolean checkParent) {
                isCheckParent = checkParent;
            }

            public static   class ListBean{
                private boolean isCheckChirld=false;
                private String month;
                private String yearMonth;
                private String paidMoney;
                private String receivableId;
                private String adjustMoney;
                private String cycleStartDate;
                private String cycleEndDate;
                private String receivableAmount;
                private String breakMoney;
                private String totalReceivableAmount;

                public String getMonth() {
                    return this.month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getYearMonth() {
                    return this.yearMonth;
                }

                public void setYearMonth(String yearMonth) {
                    this.yearMonth = yearMonth;
                }

                public String getPaidMoney() {
                    return this.paidMoney;
                }

                public void setPaidMoney(String paidMoney) {
                    this.paidMoney = paidMoney;
                }

                public String getReceivableId() {
                    return this.receivableId;
                }

                public void setReceivableId(String receivableId) {
                    this.receivableId = receivableId;
                }

                public String getAdjustMoney() {
                    return this.adjustMoney;
                }

                public void setAdjustMoney(String adjustMoney) {
                    this.adjustMoney = adjustMoney;
                }

                public String getCycleStartDate() {
                    return this.cycleStartDate;
                }

                public void setCycleStartDate(String cycleStartDate) {
                    this.cycleStartDate = cycleStartDate;
                }

                public String getCycleEndDate() {
                    return this.cycleEndDate;
                }

                public void setCycleEndDate(String cycleEndDate) {
                    this.cycleEndDate = cycleEndDate;
                }

                public String getReceivableAmount() {
                    return this.receivableAmount;
                }

                public void setReceivableAmount(String receivableAmount) {
                    this.receivableAmount = receivableAmount;
                }

                public String getBreakMoney() {
                    return this.breakMoney;
                }

                public void setBreakMoney(String breakMoney) {
                    this.breakMoney = breakMoney;
                }

                public String getTotalReceivableAmount() {
                    return this.totalReceivableAmount;
                }

                public void setTotalReceivableAmount(String totalReceivableAmount) {
                    this.totalReceivableAmount = totalReceivableAmount;
                }

                public boolean isCheckChirld() {
                    return isCheckChirld;
                }

                public void setCheckChirld(boolean checkChirld) {
                    isCheckChirld = checkChirld;
                }
            }
            public String getFeeItemCode() {
                return this.feeItemCode;
            }

            public void setFeeItemCode(String feeItemCode) {
                this.feeItemCode = feeItemCode;
            }

            public String getChargeTypeName() {
                return this.chargeTypeName;
            }

            public void setChargeTypeName(String chargeTypeName) {
                this.chargeTypeName = chargeTypeName;
            }

            public List<ListBean> getList() {
                return this.list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public String getFeeItemName() {
                return this.feeItemName;
            }

            public void setFeeItemName(String feeItemName) {
                this.feeItemName = feeItemName;
            }

            public String getChargeTypeCode() {
                return this.chargeTypeCode;
            }

            public void setChargeTypeCode(String chargeTypeCode) {
                this.chargeTypeCode = chargeTypeCode;
            }

        }
        public List<PaymentList> getPaymentList() {
            return this.paymentList;
        }

        public void setPaymentList(List<PaymentList> paymentList) {
            this.paymentList = paymentList;
        }

        public long getTotalPage() {
            return this.totalPage;
        }

        public void setTotalPage(long totalPage) {
            this.totalPage = totalPage;
        }

        public String getHouseId() {
            return this.houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

    }
    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public long getCode() {
        return this.code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
