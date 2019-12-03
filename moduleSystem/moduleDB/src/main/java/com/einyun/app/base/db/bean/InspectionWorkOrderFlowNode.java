package com.einyun.app.base.db.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class InspectionWorkOrderFlowNode{
    private String F_WK_CONTENT;
    private String F_WK_ID;
    private String F_WK_NODE;
    private String F_WK_RESULT;

    public String getF_WK_CONTENT() {
        return F_WK_CONTENT;
    }

    public void setF_WK_CONTENT(String f_WK_CONTENT) {
        F_WK_CONTENT = f_WK_CONTENT;
    }

    public String getF_WK_ID() {
        return F_WK_ID;
    }

    public void setF_WK_ID( String f_WK_ID) {
        F_WK_ID = f_WK_ID;
    }

    public String getF_WK_NODE() {
        return F_WK_NODE;
    }

    public void setF_WK_NODE(String f_WK_NODE) {
        F_WK_NODE = f_WK_NODE;
    }

    public String getF_WK_RESULT() {
        return F_WK_RESULT;
    }

    public void setF_WK_RESULT(String f_WK_RESULT) {
        F_WK_RESULT = f_WK_RESULT;
    }
}
