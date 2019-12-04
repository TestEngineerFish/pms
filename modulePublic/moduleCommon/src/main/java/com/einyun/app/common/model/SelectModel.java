package com.einyun.app.common.model;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SelectModel implements Cloneable{

    private String id;//标签id
    private String content;//标签名
    private String type="";//分类
    private boolean isCheck=false;//标签是否被选中
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
