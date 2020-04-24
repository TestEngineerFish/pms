package com.einyun.app.pms.toll.model;

import java.util.List;

public class JumpRequest {

    /**
     * divideId : 11
     * houseId : 22
     * feeList : [{"feeId":""},{"feeId":""}]
     */

    private String divideId;
    private String houseId;
    private List<FeeListBean> feeList;

    public String getDivideId() {
        return divideId;
    }

    public void setDivideId(String divideId) {
        this.divideId = divideId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public List<FeeListBean> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<FeeListBean> feeList) {
        this.feeList = feeList;
    }

    public static class FeeListBean {
        /**
         * feeId :
         */

        private String receivableId;

        public FeeListBean(String feeId) {
            this.receivableId = feeId;
        }

        public String getFeeId() {
            return receivableId;
        }

        public void setFeeId(String feeId) {
            this.receivableId = feeId;
        }
    }
}
