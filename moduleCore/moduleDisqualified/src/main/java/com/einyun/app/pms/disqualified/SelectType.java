package com.einyun.app.pms.disqualified;

public enum SelectType {
    AGING("分期", "aging"),
    CHECK_DATE("检查日期", "check_date"),
    LINE("条线", "line"),
    SEVERITY("严重程度", "severity"),
    DEADLINE("纠正截止日期", "deadline"),
    INSPECTED("被检查人", "inspected");
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