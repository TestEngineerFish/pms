package com.einyun.app.pms.toll.model;

import com.google.gson.annotations.SerializedName;

public class JumpAdvanceModel {

    /**
     * msg : SUCCESS
     * code : 0
     * data : {"errcode":0,"errmsg":"OK","data":{"39b5c285-f53e-4c67-a15d-7bf7ba865c4b":true}}
     * state : true
     * request_id : xxx
     */

    private String msg;
    private int code;
    private DataBeanX data;
    private boolean state;
    private String request_id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public static class DataBeanX {
        /**
         * errcode : 0
         * errmsg : OK
         * data : {"39b5c285-f53e-4c67-a15d-7bf7ba865c4b":true}
         */

        private int errcode;
        private String errmsg;
        private DataBean data;

        public int getErrcode() {
            return errcode;
        }

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * 39b5c285-f53e-4c67-a15d-7bf7ba865c4b : true
             */

            @SerializedName("39b5c285-f53e-4c67-a15d-7bf7ba865c4b")
            private boolean _$39b5c285f53e4c67a15d7bf7ba865c4b;

            public boolean is_$39b5c285f53e4c67a15d7bf7ba865c4b() {
                return _$39b5c285f53e4c67a15d7bf7ba865c4b;
            }

            public void set_$39b5c285f53e4c67a15d7bf7ba865c4b(boolean _$39b5c285f53e4c67a15d7bf7ba865c4b) {
                this._$39b5c285f53e4c67a15d7bf7ba865c4b = _$39b5c285f53e4c67a15d7bf7ba865c4b;
            }
        }
    }
}
