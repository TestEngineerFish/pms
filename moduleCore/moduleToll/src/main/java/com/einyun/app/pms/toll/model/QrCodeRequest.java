package com.einyun.app.pms.toll.model;

import java.util.List;

public class QrCodeRequest {

    /**
     * divideId : 11
     * houseId : 22
     * feeList : [{"feeId":""},{"feeId":""}]
     */

    private int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
