package com.einyun.app.pms.toll.model;

import java.util.List;

public class DicRelationModel {

    /**
     * code : 0
     * data : [{"dicIndex":1,"dicKey":"house_client_rel_relation_0","dicText":"业主","dicValue":"0","enabledFlag":1,"id":"92f0efc5-b4b0-11e7-b4b3-00163e0f775c","isDeleted":0,"parentId":"56ba3f4a-b4b0-11e7-b4b3-00163e0f775c","rowVersion":0,"updationDate":1508405614000},{"dicIndex":1,"dicKey":"house_client_rel_relation_1","dicText":"租客","dicValue":"1","enabledFlag":1,"id":"9db7169e-b4b0-11e7-b4b3-00163e0f775c","isDeleted":0,"parentId":"56ba3f4a-b4b0-11e7-b4b3-00163e0f775c","rowVersion":0,"updationDate":1508405632000},{"dicIndex":1,"dicKey":"house_client_rel_relation_2","dicText":"家庭成员","dicValue":"2","enabledFlag":1,"id":"a72b608a-b4b0-11e7-b4b3-00163e0f775c","isDeleted":0,"parentId":"56ba3f4a-b4b0-11e7-b4b3-00163e0f775c","rowVersion":0,"updationDate":1508405648000},{"dicIndex":1,"dicKey":"house_client_rel_relation_3","dicText":"同住人","dicValue":"3","enabledFlag":1,"id":"6c5426c7-c517-11e7-b4b3-00163e0f775c","isDeleted":0,"parentId":"56ba3f4a-b4b0-11e7-b4b3-00163e0f775c","rowVersion":0,"updationDate":1508405648000},{"dicIndex":1,"dicKey":"house_client_rel_relation_4","dicText":"开发商","dicValue":"4","enabledFlag":1,"id":"7dc98c56-31a0-11e8-940f-7cd30adaaf52","isDeleted":0,"parentId":"56ba3f4a-b4b0-11e7-b4b3-00163e0f775c","rowVersion":0,"updationDate":1522142600970},{"description":"房客关系访客","dicIndex":1,"dicKey":"house_client_rel_relation_5","dicText":"访客","dicValue":"5","enabledFlag":1,"id":"2c035290-7387-11e8-8282-00163e0f775c","isDeleted":0,"parentId":"56ba3f4a-b4b0-11e7-b4b3-00163e0f775c","rowVersion":0,"updationDate":1529388503129}]
     * msg : OK
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dicIndex : 1
         * dicKey : house_client_rel_relation_0
         * dicText : 业主
         * dicValue : 0
         * enabledFlag : 1
         * id : 92f0efc5-b4b0-11e7-b4b3-00163e0f775c
         * isDeleted : 0
         * parentId : 56ba3f4a-b4b0-11e7-b4b3-00163e0f775c
         * rowVersion : 0
         * updationDate : 1508405614000
         * description : 房客关系访客
         */

        private int dicIndex;
        private String dicKey;
        private String dicText;
        private String dicValue;
        private int enabledFlag;
        private String id;
        private int isDeleted;
        private String parentId;
        private int rowVersion;
        private long updationDate;
        private String description;

        public int getDicIndex() {
            return dicIndex;
        }

        public void setDicIndex(int dicIndex) {
            this.dicIndex = dicIndex;
        }

        public String getDicKey() {
            return dicKey;
        }

        public void setDicKey(String dicKey) {
            this.dicKey = dicKey;
        }

        public String getDicText() {
            return dicText;
        }

        public void setDicText(String dicText) {
            this.dicText = dicText;
        }

        public String getDicValue() {
            return dicValue;
        }

        public void setDicValue(String dicValue) {
            this.dicValue = dicValue;
        }

        public int getEnabledFlag() {
            return enabledFlag;
        }

        public void setEnabledFlag(int enabledFlag) {
            this.enabledFlag = enabledFlag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getRowVersion() {
            return rowVersion;
        }

        public void setRowVersion(int rowVersion) {
            this.rowVersion = rowVersion;
        }

        public long getUpdationDate() {
            return updationDate;
        }

        public void setUpdationDate(long updationDate) {
            this.updationDate = updationDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
