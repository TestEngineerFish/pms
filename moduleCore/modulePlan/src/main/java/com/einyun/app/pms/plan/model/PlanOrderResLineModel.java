package com.einyun.app.pms.plan.model;

import java.util.List;

public class PlanOrderResLineModel {

    /**
     * id : 498243
     * typeId : 483534
     * key : ld
     * name : 楼栋
     * parentId : 483534
     * open : true
     * children : []
     * text : 楼栋
     * isParent : false
     */

    private String id;
    private String typeId;
    private String key;
    private String name;
    private String parentId;
    private String open;
    private String text;
    private String isParent;
    private List<?> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }
}
