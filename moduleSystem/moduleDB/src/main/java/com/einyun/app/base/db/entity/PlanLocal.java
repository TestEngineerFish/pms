package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.converter.ResourcesTypeConvert;
import com.einyun.app.base.db.converter.StringTypeConvert;
import com.einyun.app.base.db.converter.WorkNoteTypeConvert;

import java.util.List;

@Entity(tableName = "plan_local",primaryKeys = {"orderId","userId"})
public class PlanLocal {
    private String note;
    private String designatePerson;
    private String remark;
    @NonNull
    private String userId;
    @NonNull
    private String orderId;

    @TypeConverters(StringTypeConvert.class)
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @TypeConverters(WorkNoteTypeConvert.class)
    private List<WorkNode> nodes;

    @TypeConverters(ResourcesTypeConvert.class)
    private List<PlanInfo.Data.Zyjhgd.Sub_jhgdzyb> resources;
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId( @NonNull String userId) {
        this.userId = userId;
    }

    public List<WorkNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<WorkNode> nodes) {
        this.nodes = nodes;
    }

    @NonNull
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(@NonNull String orderId) {
        this.orderId = orderId;
    }

    public String getDesignatePerson() {
        return designatePerson;
    }

    public void setDesignatePerson(String designatePerson) {
        this.designatePerson = designatePerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PlanInfo.Data.Zyjhgd.Sub_jhgdzyb> getResources() {
        return resources;
    }

    public void setResources(List<PlanInfo.Data.Zyjhgd.Sub_jhgdzyb> resources) {
        this.resources = resources;
    }
}
