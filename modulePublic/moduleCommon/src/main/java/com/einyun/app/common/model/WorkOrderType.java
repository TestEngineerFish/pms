package com.einyun.app.common.model;

public enum  WorkOrderType {
    DISTRIBUTE("distribute"),PLAN("plan"),PATROL("patrol");

    private String type;

    WorkOrderType(String type){
        this.type=type;
    }

    public String type(){
        return type;
    }
}
