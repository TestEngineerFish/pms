package com.einyun.app.common.model;

public enum ListType {
    PENDING(1),DONE(2);

    private int type;

    ListType(int type){
        this.type=type;
    }

    public int getType(){
        return type;
    }
}
