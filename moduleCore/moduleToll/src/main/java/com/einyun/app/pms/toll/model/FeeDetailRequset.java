package com.einyun.app.pms.toll.model;

import java.util.List;

public class FeeDetailRequset {

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
    private String clientId;
    private String houseId;
    private List<String> lables;
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<String> getLables() {
        return lables;
    }

    public void setLables(List<String> lables) {
        this.lables = lables;
    }
}
