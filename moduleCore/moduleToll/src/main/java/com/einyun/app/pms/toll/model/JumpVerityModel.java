package com.einyun.app.pms.toll.model;

public class JumpVerityModel {

    /**
     * data : false
     * errcode : 0
     * errmsg : OK
     */

    private boolean data;
    private int errcode;
    private String errmsg;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
