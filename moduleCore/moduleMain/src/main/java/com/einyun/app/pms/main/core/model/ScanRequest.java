package com.einyun.app.pms.main.core.model;

public class ScanRequest {

    /**
     * id : 586c9d05bef449e6a4cbc2e4d29d2bc2
     * pageSize : 20
     * page : 1
     * userId : 213123213
     */

    private String id;
    private String resId;
    private int pageSize;
    private int page;
    private String userId;
    private String orderOverTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getOrderOverTime() {
        return orderOverTime;
    }

    public void setOrderOverTime(String orderOverTime) {
        this.orderOverTime = orderOverTime;
    }
}
