package com.einyun.app.pms.create;

public enum SelectType {
    AGING("分期", "sss"), LINE("条线", "bbb"),WORKY_TYPE("工单类型","")
    ,RESOURCE_TYPE("资源类型","")
    ,RESOURCE("资源","")
    ,DISPOSE_PERSON("处理人","dispose_person");
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