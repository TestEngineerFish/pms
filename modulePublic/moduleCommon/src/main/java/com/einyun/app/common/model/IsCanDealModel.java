package com.einyun.app.common.model;

public class IsCanDealModel {

    /**
     * canTurn : false
     * businessKey : 85256822033804294
     * showDetail : true
     */

    private boolean canTurn;
    private String businessKey;
    private boolean showDetail;

    public boolean isCanTurn() {
        return canTurn;
    }

    public void setCanTurn(boolean canTurn) {
        this.canTurn = canTurn;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }
}
