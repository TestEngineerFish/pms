package com.einyun.app.pms.toll.model;

import java.util.List;

public class JumpAdvanceRequset {

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

    public List<String> getFeeItemIds() {
        return feeItemIds;
    }

    public void setFeeItemIds(List<String> feeItemIds) {
        this.feeItemIds = feeItemIds;
    }

    /**
     * divideId : 1
     * type : 1
     *  grid : 1
     *  buildingId : [""]
     *  untiId : [""]
     *  houseIdS  : ["",""]
     *  isArrears  : 1
     */

    private String divideId;
    private String houseId;
    private List<String> feeItemIds;



}
