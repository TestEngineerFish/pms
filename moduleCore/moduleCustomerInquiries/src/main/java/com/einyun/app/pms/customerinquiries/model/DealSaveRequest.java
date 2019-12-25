package com.einyun.app.pms.customerinquiries.model;

public class DealSaveRequest {


    /**
     * ID_ : 66465331870172164
     * bizData : {"handle_cont":"12121"}
     */

    private String ID_;
    private BizDataBean bizData;

    public DealSaveRequest() {
        this.bizData = new BizDataBean();
    }

    public String getID_() {
        return ID_;
    }

    public void setID_(String ID_) {
        this.ID_ = ID_;
    }

    public BizDataBean getBizData() {
        return bizData;
    }

    public void setBizData(BizDataBean bizData) {
        this.bizData = bizData;
    }

    public static class BizDataBean {
        /**
         * handle_cont : 12121
         */

        private String handle_cont;

        public String getHandle_cont() {
            return handle_cont;
        }

        public void setHandle_cont(String handle_cont) {
            this.handle_cont = handle_cont;
        }
    }
}
