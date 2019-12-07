package com.einyun.app.common.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SelectModel implements Cloneable{

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
    private List<?> children;
    private String isParent;
    private List<SelectModel> selectModelList=new ArrayList<>();
    public SelectModel() {

    }

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

    public SelectModel(String id, String content, boolean isCheck) {
        this.id = id;
        this.content = content;
        this.isCheck = isCheck;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        SelectModel model=(SelectModel)obj;
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

    public List<SelectModel> getSelectModelList() {
        return selectModelList;
    }

    public void setSelectModelList(List<SelectModel> selectModelList) {
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

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }
}
