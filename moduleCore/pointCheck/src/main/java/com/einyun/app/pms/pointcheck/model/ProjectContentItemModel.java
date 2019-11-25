package com.example.shimaostaff.pointcheck.model;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.model
 * @ClassName: ProjectContentItemModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/08 16:52
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 16:52
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ProjectContentItemModel {
    private String id;
    private String checkContent;
    private int checkType;
    private String remark;
    private int checkResult=-1;
    private int qualified;
    private String minValue;
    private String maxVal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public int getCheckType() {
        return checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    public int getQualified() {
        return qualified;
    }

    public void setQualified(int qualified) {
        this.qualified = qualified;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }
}
