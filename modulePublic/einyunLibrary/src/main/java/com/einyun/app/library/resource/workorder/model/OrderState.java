package com.einyun.app.library.resource.workorder.model;

public enum OrderState{
NEW(1),HANDING(2),PROCESSING(3),CLOSED(4),PENDING(5),OVER_DUE(6);
    //必须增加一个构造函数,变量,得到该变量的值
    private int mState = 0;

    private OrderState(int value) {
        mState = value;
    }

    /**
     * @return 枚举变量实际返回值
     */
    public int getState() {
        return mState;
    }

}
