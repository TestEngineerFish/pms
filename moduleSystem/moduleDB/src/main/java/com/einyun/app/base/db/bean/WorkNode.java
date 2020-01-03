package com.einyun.app.base.db.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class WorkNode implements Serializable {
    private int pos;
    private int id;
    public  String number;
    public  String workThings;
    public  String workNode;
    // 结果可以修改
    public String result;

    /**
     * 巡更更新字段
     */
    public int is_photo;
    public String tenant_id;
    public String pic_example_url;
    public String patrol_items;
    public String sign_type;
    public int sort;
    public String patrol_point_id;
    public String pic_url;
    public int sign_result;
    public String sign_time;
    private List<String> cachedImages;
    private int F_WK_RESULT;

    public WorkNode(){}

    public WorkNode(String number, String workThings, String workNode, String result) {
        this.number = number;
        this.workThings = workThings;
        this.workNode = workNode;
        this.result = result;
    }

    public WorkNode(String number, String workThings, String workNode,String sign_type,int sign_result,int is_photo,String result,String sign_time,int sort,String patrol_point_id,String pic_example_url,String patrol_items,String picUrl) {
        this.number = number;
        this.workThings = workThings;
        this.workNode = workNode;
        this.sign_type=sign_type;
        this.sign_result=sign_result;
        this.is_photo=is_photo;
        this.result = result;
        this.sign_time=sign_time;
        this.sort=sort;
        this.patrol_point_id=patrol_point_id;
        this.pic_example_url=pic_example_url;
        this.patrol_items=patrol_items;
        this.pic_url=picUrl;
    }

    public List<String> getCachedImages() {
        return cachedImages;
    }

    public void setCachedImages(List<String> cachedImages) {
        this.cachedImages = cachedImages;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWorkThings() {
        return workThings;
    }

    public void setWorkThings(String workThings) {
        this.workThings = workThings;
    }

    public String getWorkNode() {
        return workNode;
    }

    public void setWorkNode(String workNode) {
        this.workNode = workNode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getF_WK_RESULT() {
        return F_WK_RESULT;
    }

    public void setF_WK_RESULT(int f_WK_RESULT) {
        F_WK_RESULT = f_WK_RESULT;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
