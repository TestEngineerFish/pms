package com.einyun.app.pms.approval.model;

public class FormData {

    /**
     * apply_reason : 测试强制闭单
     * category : 客户问询工单
     * code : community_JDDJLW-GC-WX-20191226110003
     * type : 工程维修类
     */

    private String apply_reason;
    private String category;
    private String code;
    private String type;

    public String getApply_reason() {
        return apply_reason;
    }

    public void setApply_reason(String apply_reason) {
        this.apply_reason = apply_reason;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type==null?"":type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
