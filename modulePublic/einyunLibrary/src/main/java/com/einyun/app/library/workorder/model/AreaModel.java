package com.einyun.app.library.workorder.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AreaModel implements Cloneable{

    private int grade;
    private String id="";//标签id
    private String content;//标签名
    private String type="";//分类
    private boolean isCheck=false;//标签是否被选中
    @NonNull
    private String conditionType;
    private String typeId;
    private String key;
    private String name;
    private String parentId;
    private String open;
    private String text;
    private List<AreaModel> children;
    private String isParent;
    private String categoryId;
    private String dataName;
    private String dataKey;
    private String enabledFlag;

    public void setChildren(List<AreaModel> children) {
        this.children = children;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    private int sn;
    //    private SelectModel parent;
    private List<AreaModel> selectModelList=new ArrayList<>();
    public AreaModel() {

    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(@NonNull int grade) {
        this.grade = grade;
    }

//
//    public void setParent(SelectModel parent) {
//        this.parent = parent;
//        this.setGrade(parent.getGrade()+1);
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public AreaModel(String id, String content, boolean isCheck) {
        this.id = id;
        this.content = content;
        this.isCheck = isCheck;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        AreaModel model=(AreaModel)obj;
        return this.id.equals(model.id);

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<AreaModel> getSelectModelList() {
        return selectModelList;
    }

    public void setSelectModelList(List<AreaModel> selectModelList) {
        this.selectModelList = selectModelList;
    }

    @NonNull
    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(@NonNull String conditionType) {
        this.conditionType = conditionType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public List<AreaModel> getChildren() {
        return children;
    }
}
