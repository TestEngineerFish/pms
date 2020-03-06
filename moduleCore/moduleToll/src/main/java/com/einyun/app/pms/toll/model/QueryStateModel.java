package com.einyun.app.pms.toll.model;

public class QueryStateModel {

    /**
     * createTime : 1582959094000
     * divideId : 7
     * houseId : ops-2227ED3D-AA42-4C18-93F8-204BB35AFD38
     * houseName : 20-02-1206
     * id : 223
     * isUsePoint : 0
     * openId : waiting
     * payAmount : 1.36
     * payMethod : -1
     * payOrderId : 202002291451341895895
     * payOrderType : pty-101
     * payStatus : 0
     * qrcodeUrl : https://syb.allinpay.com/apiweb/h5unionpay/unionorder?appid=00000003&body=%E7%89%A9%E4%B8%9A%E7%BC%B4%E8%B4%B9&charset=utf-8&cusid=990440148166000&notify_url=http%3A%2F%2F106.75.162.186%3A10018%2FpayInfo%2Fcallback&randomstr=53779976&remark=&reqsn=202002291451341895895&returl=http%3A%2F%2Fwww.baidu.com&sign=d93edb07e929eabdf0ff881edc7fa7c9&trxamt=136&version=12
     * userId : 66929423487598598
     */

    private long createTime;
    private String divideId;
    private String houseId;
    private String houseName;
    private int id;
    private int isUsePoint;
    private String openId;
    private double payAmount;
    private int payMethod;
    private String payOrderId;
    private String payOrderType;
    private int payStatus;
    private String qrcodeUrl;
    private String userId;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

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

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsUsePoint() {
        return isUsePoint;
    }

    public void setIsUsePoint(int isUsePoint) {
        this.isUsePoint = isUsePoint;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getPayOrderType() {
        return payOrderType;
    }

    public void setPayOrderType(String payOrderType) {
        this.payOrderType = payOrderType;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
