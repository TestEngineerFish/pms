package com.einyun.app.pms.approval.model;

import java.util.List;

/**
 * Create by dowedo on 2019/4/24
 */
public class GetByTypeKeyInnerAuditStatusModule {

    /**
     * id : 942012
     * typeId : 54fdbf93972246d4b9a628a1fa44e9ba
     * key : submit
     * name : 提交审批
     * parentId : 54fdbf93972246d4b9a628a1fa44e9ba
     * open : true
     * isParent : false
     * children : []
     * text : 提交审批
     */

    private String id;
    private String typeId;
    private String key;
    private String name;
    private String parentId;
    private String open;
    private String isParent;
    private String text;
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

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
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
}
