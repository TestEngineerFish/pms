package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "checkpoints")
public class CheckPoint {
    @NonNull
    private String id;
    @NonNull
    private String userId;
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
    private long saveTime;
    @PrimaryKey(autoGenerate = true)
    private int id_;
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getCheckRecordCode() {
        return checkRecordCode;
    }

    public void setCheckRecordCode(String checkRecordCode) {
        this.checkRecordCode = checkRecordCode;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
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

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
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

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }
}
