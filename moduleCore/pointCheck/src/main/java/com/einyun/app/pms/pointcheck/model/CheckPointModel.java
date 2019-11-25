package com.einyun.app.pms.pointcheck.model;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.model
 * @ClassName: CheckPointModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 13:43
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 13:43
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CheckPointModel {
    private String id;
    private String checkRecordCode;
    private String massifId;
    private String remark;
    private int isUnusual;
    private String createTime;
    private String createId;
    private String massifName;
    private String createName;
    private String checkName;
    private String specificLocation;
    private int isPic;
    private String resourceName;
//    private List<PicUrlModel> picUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckRecordCode() {
        return checkRecordCode;
    }

    public void setCheckRecordCode(String checkRecordCode) {
        this.checkRecordCode = checkRecordCode;
    }

    public String getMassifId() {
        return massifId;
    }

    public void setMassifId(String massifId) {
        this.massifId = massifId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsUnusual() {
        return isUnusual;
    }

    public void setIsUnusual(int isUnusual) {
        this.isUnusual = isUnusual;
    }

//    public List<PicUrlModel> getPicUrl() {
//        return picUrl;
//    }
//
//    public void setPicUrl(List<PicUrlModel> picUrl) {
//        this.picUrl = picUrl;
//    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getMassifName() {
        return massifName;
    }

    public void setMassifName(String massifName) {
        this.massifName = massifName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }

    public int getIsPic() {
        return isPic;
    }

    public void setIsPic(int isPic) {
        this.isPic = isPic;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }
}
