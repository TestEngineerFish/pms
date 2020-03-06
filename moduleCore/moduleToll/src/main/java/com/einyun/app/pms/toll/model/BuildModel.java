package com.einyun.app.pms.toll.model;

import java.math.BigDecimal;
import java.util.List;

public class BuildModel {


    private List<GridRangeBean> gridRange;

    public List<GridRangeBean> getGridRange() {
        return gridRange;
    }

    public void setGridRange(List<GridRangeBean> gridRange) {
        this.gridRange = gridRange;
    }

    public static class GridRangeBean {
        /**
         * checked : 0
         * code : 08-01
         * id : ops-DD6A9E9A-AE85-487E-8C75-CF143902D169
         * level : 1
         * name : 8幢1单元
         * parentId : 0
         */
        private String parentId;
        private int checked;
        private String code;
        private String id;
        private int level;
        private int houseTotal;
        private int feeHouseTotal;
        private int arrearsLevel;
        private int type;
        private String name;
        private BigDecimal feeAmount;
        private String feeTotal;
        private boolean isLoaMore;


        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public BigDecimal getFeeAmount() {
            return feeAmount==null?new BigDecimal("0"):feeAmount;
        }

        public void setFeeAmount(BigDecimal feeAmount) {
            this.feeAmount = feeAmount;
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

        public int getHouseTotal() {
            return houseTotal;
        }

        public void setHouseTotal(int houseTotal) {
            this.houseTotal = houseTotal;
        }

        public int getFeeHouseTotal() {
            return feeHouseTotal;
        }

        public void setFeeHouseTotal(int feeHouseTotal) {
            this.feeHouseTotal = feeHouseTotal;
        }

        public String getFeeTotal() {
            return feeTotal==null?"0":feeTotal;
        }

        public void setFeeTotal(String feeTotal) {
            this.feeTotal = feeTotal;
        }

        public boolean isLoaMore() {
            return isLoaMore;
        }

        public void setLoaMore(boolean loaMore) {
            isLoaMore = loaMore;
        }
    }
}
