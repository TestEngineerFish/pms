package com.einyun.app.pms.create;

public enum SelectType {
    AGING("园区", "aging"),
    LINE("条线", "line"),
    WORKY_TYPE("工单类型","work_type"),
    RESOURCE_TYPE("资源类型","resource_type"),
    RESOURCE("资源","resource"),
    DISPOSE_PERSON("处理人","dispose_person"),
    HOUSE("房产","house"),
    COMPLAIN_WAY("投诉方式","complain_way"),
    COMPLAIN_TYPE("投诉类别","complain_type"),
    COMPLAIN_NATURE("投诉性质","complain_nature"),
    ENQUIRY_WAY("问询方式","ENQUIRY_WAY"),
    ENQUIRY_TYPE("问询类别","ENQUIRY_Type"),
    ENQUIRY_TYPE_BIG("问询大类","ENQUIRY_Type_Big"),
    ENQUIRY_TYPE_SMALL("问询小类","ENQUIRY_Type_Small"),
    REPAIRS_TYPE("报修类别","REPAIRS_TYPE"),
    REPAIRS_WAY("报修方式","REPAIRS_WAY"),
    REPAIRS_NATURE("报修性质","REPAIRS_NATURE"),
    REPAIRS_LOCATION("报修区域","REPAIRS_LOCATION"),
    REPAIRS_TIME("预约上门时间","REPAIRS_TIME"),
    OLD_CODE("原工单号","OLD_CODE");
    // 成员变量
    private String name;
    private String index;

    // 构造方法  
    private SelectType(String name, String index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法  
    public static String getName(String index) {
        for (SelectType c : SelectType.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}