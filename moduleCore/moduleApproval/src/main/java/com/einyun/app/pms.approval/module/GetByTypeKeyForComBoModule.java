package com.einyun.app.pms.approval.module;

import java.util.List;

/**
 * Create by dowedo on 2019/4/24
 */
public class GetByTypeKeyForComBoModule {

    private String id;
    private String typeId;
    private String key;
    private String name;
    private String parentId;
    private String open;
    private String isParent;
    private String text;
    private List<ChildrenBean> children;

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

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 942020
         * typeId : df5708a976de4e239938b681cb4ba92a
         * key : INVALID_COMPLAIN
         * name : 无效投诉
         * parentId : 942016
         * open : true
         * isParent : false
         * children : []
         * text : 无效投诉
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
}
