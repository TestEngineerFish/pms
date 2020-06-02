package com.einyun.app.pms.toll.model;

public class GetNameRequset {


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
    private String cellphone;

    public String getDivideId() {
        return divideId;
    }

    public void setDivideId(String divideId) {
        this.divideId = divideId;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
