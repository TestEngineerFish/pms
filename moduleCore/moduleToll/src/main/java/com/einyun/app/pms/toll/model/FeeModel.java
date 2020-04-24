package com.einyun.app.pms.toll.model;

import java.math.BigDecimal;
import java.util.List;

public class FeeModel {

    /**
     * code : 0
     * data : {"feeList":[{"buildingId":"ops-DDFCD9D9-57B9-4D99-AB4E-B1AA2B5B6F87","feeAmount":1676.89,"houseTotal":3,"type":1},{"buildingId":"ops-23C1C0DF-F10A-40AE-9E2A-11C66D24AD34","feeAmount":1509.71,"houseTotal":1,"type":1},{"buildingId":"ops-180C8E8B-E2D9-4B5B-9E45-D393B0B7C295","feeAmount":5601.5,"houseTotal":3,"type":1},{"buildingId":"ops-D69E1A51-3E85-4885-BA87-F8DFF5DAB26F","feeAmount":3269.14,"houseTotal":2,"type":1},{"buildingId":"ops-5279D1D1-476F-4009-BBCA-5C94979DC94F","feeAmount":2472.82,"houseTotal":2,"type":1},{"buildingId":"ops-F382C932-E24C-43B2-8B3B-2B4B9E2E070D","feeAmount":1394.64,"houseTotal":1,"type":1},{"buildingId":"ops-ABE098BE-0E10-49D0-813E-322EEBC8B095","feeAmount":3056.28,"houseTotal":2,"type":1},{"buildingId":"ops-203C0891-8A0F-447D-95DC-5930023C2EBC","feeAmount":1289.64,"houseTotal":1,"type":1}]}
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
        private List<FeeListBean> feeList;

        public List<FeeListBean> getFeeList() {
            return feeList;
        }

        public void setFeeList(List<FeeListBean> feeList) {
            this.feeList = feeList;
        }

        public static class FeeListBean {
            /**
             * buildingId : ops-DDFCD9D9-57B9-4D99-AB4E-B1AA2B5B6F87
             * feeAmount : 1676.89
             * houseTotal : 3
             * type : 1
             */

            private String buildingId;
            private String unitId;
            private String name;

            public String getUnitId() {
                return unitId;
            }

            public void setUnitId(String unitId) {
                this.unitId = unitId;
            }

            public String getHouseId() {
                return houseId;
            }

            public void setHouseId(String houseId) {
                this.houseId = houseId;
            }

            private String houseId;
            private int arrearsLevel;
            private BigDecimal feeAmount;
            private int houseTotal;
            private int feeHouseTotal;
            private int type;
            private String feeTotal;
            public String getBuildingId() {
                return buildingId;
            }

            public void setBuildingId(String buildingId) {
                this.buildingId = buildingId;
            }

            public BigDecimal getFeeAmount() {
                return feeAmount==null?new BigDecimal("0"):feeAmount;
            }

            public void setFeeAmount(BigDecimal feeAmount) {
                this.feeAmount = feeAmount;
            }

            public int getHouseTotal() {
                return houseTotal;
            }

            public void setHouseTotal(int houseTotal) {
                this.houseTotal = houseTotal;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getArrearsLevel() {
                return arrearsLevel;
            }

            public void setArrearsLevel(int arrearsLevel) {
                this.arrearsLevel = arrearsLevel;
            }

            public int getFeeHouseTotal() {
                return feeHouseTotal;
            }

            public void setFeeHouseTotal(int feeHouseTotal) {
                this.feeHouseTotal = feeHouseTotal;
            }

            public String getFeeTotal() {
                return feeTotal;
            }

            public void setFeeTotal(String feeTotal) {
                this.feeTotal = feeTotal;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
