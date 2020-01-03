package com.einyun.app.base.db.bean;


import java.io.Serializable;

public class SubInspectionWorkOrderFlowNode implements Serializable {
    private String ref_id_;
    private String F_WK_CONTENT;
    private String F_WK_ID;
    private String id_;
    private String F_WK_NODE;
    private String F_WK_RESULT;
    private String proc_inst_id_;

    //巡更新新字段
    private int is_photo;
    private String tenant_id;
    private String pic_example_url;
    private String patrol_items;
    private String sign_type;
    private int sort;
    private String patrol_point_id;
    private String pic_url;
    private int sign_result;
    private String sign_time;

    public String getRef_id_() {
        return ref_id_;
    }

    public void setRef_id_(String ref_id_) {
        this.ref_id_ = ref_id_;
    }

    public String getF_WK_CONTENT() {
        return F_WK_CONTENT;
    }

    public void setF_WK_CONTENT(String f_WK_CONTENT) {
        F_WK_CONTENT = f_WK_CONTENT;
    }

    public String getF_WK_ID() {
        return F_WK_ID;
    }

    public void setF_WK_ID(String f_WK_ID) {
        F_WK_ID = f_WK_ID;
    }

    public String getId_() {
        return id_;
    }

    public void setId_( String id_) {
        this.id_ = id_;
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

    public String getProc_inst_id_() {
        return proc_inst_id_;
    }

    public void setProc_inst_id_(String proc_inst_id_) {
        this.proc_inst_id_ = proc_inst_id_;
    }

    public int getIs_photo() {
        return is_photo;
    }

    public void setIs_photo(int is_photo) {
        this.is_photo = is_photo;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getPic_example_url() {
        return pic_example_url;
    }

    public void setPic_example_url(String pic_example_url) {
        this.pic_example_url = pic_example_url;
    }

    public String getPatrol_items() {
        return patrol_items;
    }

    public void setPatrol_items(String patrol_items) {
        this.patrol_items = patrol_items;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPatrol_point_id() {
        return patrol_point_id;
    }

    public void setPatrol_point_id(String patrol_point_id) {
        this.patrol_point_id = patrol_point_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getSign_result() {
        return sign_result;
    }

    public void setSign_result(int sign_result) {
        this.sign_result = sign_result;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }
}
