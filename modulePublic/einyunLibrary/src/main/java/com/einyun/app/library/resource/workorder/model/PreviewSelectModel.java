package com.einyun.app.library.resource.workorder.model;

import java.util.List;

public class PreviewSelectModel {
        private String id;
        private String updateBy;
        private String typeGroupKey;
        private String name;
        private String typeKey;
        private int struType;
        private String parentId;
        private int depth;
        private String path;
        private String isLeaf;
        private String ownerId;
        private int sn;
        private List<PreviewSelectModel> children;
        private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeGroupKey() {
        return typeGroupKey;
    }

    public void setTypeGroupKey(String typeGroupKey) {
        this.typeGroupKey = typeGroupKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public int getStruType() {
        return struType;
    }

    public void setStruType(int struType) {
        this.struType = struType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public List<PreviewSelectModel> getChildren() {
        return children;
    }

    public void setChildren(List<PreviewSelectModel> children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
