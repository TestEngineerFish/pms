package com.einyun.app.common.model;

public enum PageUIState {
    LOADING(0), FILLDATA(1),EMPTY(2),LOAD_FAILED(3);

    private int state;
    PageUIState(int state){
        this.state=state;
    }

    public int getState(){
        return state;
    }
}
