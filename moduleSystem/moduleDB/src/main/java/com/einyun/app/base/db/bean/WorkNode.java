package com.einyun.app.base.db.bean;

public class WorkNode {
    public static String RESULT_PASS="1";
    public static String RESULT_REJECT="0";
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

    public WorkNode(){}

    public WorkNode(String number, String workThings, String workNode, String result) {
        this.number = number;
        this.workThings = workThings;
        this.workNode = workNode;
        this.result = result;
    }

    public WorkNode(String number, String workThings, String workNode, String result,String sign_time,int sort,String patrol_point_id,String pic_example_url,String patrol_items) {
        this.number = number;
        this.workThings = workThings;
        this.workNode = workNode;
        this.result = result;
        this.sign_time=sign_time;
        this.sort=sort;
        this.patrol_point_id=patrol_point_id;
        this.pic_example_url=pic_example_url;
        this.patrol_items=patrol_items;
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
}
