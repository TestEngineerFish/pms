package com.einyun.app.pms.toll.model;

public class CreateOrderModel {

    /**
     * msg : 生成缴费订单成功
     * code : 0
     * data : 197
     * state : true
     * request_id : xxx
     */

    private String msg;
    private int code;
    private int data;
    private boolean state;
    private String request_id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
