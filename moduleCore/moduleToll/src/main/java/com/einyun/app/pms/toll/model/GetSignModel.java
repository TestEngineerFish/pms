package com.einyun.app.pms.toll.model;

import java.util.List;

public class GetSignModel {

    /**
     * code : 0
     * msg : null
     * data : {"ownerId":"69090357899427846","divideId":"ops-ED4D76F8-5381-438C-9C53-C5B8D1B86475","tagList":[{"tagName":"自定义标签","tagValue":"哈哈哈"}]}
     */

    private int code;
    private Object msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ownerId : 69090357899427846
         * divideId : ops-ED4D76F8-5381-438C-9C53-C5B8D1B86475
         * tagList : [{"tagName":"自定义标签","tagValue":"哈哈哈"}]
         */

        private String ownerId;
        private String divideId;
        private List<TagListBean> tagList;

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getDivideId() {
            return divideId;
        }

        public void setDivideId(String divideId) {
            this.divideId = divideId;
        }

        public List<TagListBean> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListBean> tagList) {
            this.tagList = tagList;
        }

        public static class TagListBean {
            /**
             * tagName : 自定义标签
             * tagValue : 哈哈哈
             */

            private String tagName;
            private String tagValue;
            private int checked;

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getTagValue() {
                return tagValue==null?"":tagValue;
            }

            public void setTagValue(String tagValue) {
                this.tagValue = tagValue;
            }

            public int getChecked() {
                return checked;
            }

            public void setChecked(int checked) {
                this.checked = checked;
            }
        }
    }
}
