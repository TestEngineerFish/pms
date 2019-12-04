package com.einyun.app.pms.patrol.model;

public class WorkNode {
    public static String RESULT_PASS="1";
    public static String RESULT_REJECT="0";
    private int id;
    public  String number;
    public  String workThings;
    public  String workNode;
    // 结果可以修改
    public String result;

    public WorkNode(){}

    public WorkNode(String number, String workThings, String workNode, String result) {
        this.number = number;
        this.workThings = workThings;
        this.workNode = workNode;
        this.result = result;
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
