package com.einyun.app.pms.toll.model;

import java.util.List;

public class FeeRequset {

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
    private int operateType;
    private String operateRemark;
    private String operateTime;
    private int type;




    public List<String> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<String> buildingIds) {
        this.buildingIds = buildingIds;
    }

    public List<String> getUntiId() {
        return unitIds;
    }

    public void setUntiId(List<String> untiId) {
        this.unitIds = untiId;
    }

    public List<String> getHouseIdS() {
        return houseIdS;
    }

    public void setHouseIdS(List<String> houseIdS) {
        this.houseIdS = houseIdS;
    }


    private List<String> buildingIds;
    private List<String> unitIds;
    private List<String> houseIdS;

    public String getDivideId() {
        return divideId;
    }

    public void setDivideId(String divideId) {
        this.divideId = divideId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getOperateRemark() {
        return operateRemark;
    }

    public void setOperateRemark(String operateRemark) {
        this.operateRemark = operateRemark;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
