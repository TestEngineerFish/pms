package com.einyun.app.pms.approval.module;

/**
 * Create by dowedo on 2019/4/25
 */
public class UrlxcgdGetInstBOModule {


    /**
     * data : {"workorder_audit_model":{"divide_name":"测试苏南地块","audit_code":"QZBD20190424100003","zone_name":"测试区域","project_name":"测试苏南项目","apply_biz_id":"2dbefc822d2b4ce9ad9c3901e4bb832a","ref_id_":"0","zone_id":"35d6963e94f84558a27e850a374f16c6","district_name":"测试江浙沪城区","apply_date":"2019-04-24 10:15:24","form_data":"{\"code\":\"SFSYSFQ001-GC-BX-20190412200006\",\"workOrderCategory\":\"客户报修工单\",\"applyReason\":\"好\",\"category\":\"工程维修类\",\"repairArea\":\"公区\"}","project_id":"44766287f99647a49669ece17d52a2f4","audit_instance_id":"bd881727bb534d1b9e1beb1d169373ea","apply_flow_key":"customer_repair_flow","apply_key_title":"SFSYSFQ001-GC-BX-20190412200006","callback_status":0,"id_":"50ff4640252e4ed783baa25d54c93607","apply_user":"工程员工","audit_sub_type":"FORCE_CLOSE_REPAIR","divide_id":"0e4a9d06cd3741f088d6c0958ef02f7e","apply_user_id":"aa984e945b844a1a85b0a9d144be3d78","apply_instance_id":"cc713e6692d34327a207dfba021f16dd","district_id":"fcb181d98adc4bd284a0428751347971","audit_type":"INNER_AUDIT_FORCE_CLOSE","status":"submit","initData":{}}}
     * permission : {"fields":{"order_audit":{"apply_flow_key":"r","audit_code":"r"}},"opinion":{}}
     */

    private DataBean data;
    private String permission;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public static class DataBean {
        /**
         * workorder_audit_model : {"divide_name":"测试苏南地块","audit_code":"QZBD20190424100003","zone_name":"测试区域","project_name":"测试苏南项目","apply_biz_id":"2dbefc822d2b4ce9ad9c3901e4bb832a","ref_id_":"0","zone_id":"35d6963e94f84558a27e850a374f16c6","district_name":"测试江浙沪城区","apply_date":"2019-04-24 10:15:24","form_data":"{\"code\":\"SFSYSFQ001-GC-BX-20190412200006\",\"workOrderCategory\":\"客户报修工单\",\"applyReason\":\"好\",\"category\":\"工程维修类\",\"repairArea\":\"公区\"}","project_id":"44766287f99647a49669ece17d52a2f4","audit_instance_id":"bd881727bb534d1b9e1beb1d169373ea","apply_flow_key":"customer_repair_flow","apply_key_title":"SFSYSFQ001-GC-BX-20190412200006","callback_status":0,"id_":"50ff4640252e4ed783baa25d54c93607","apply_user":"工程员工","audit_sub_type":"FORCE_CLOSE_REPAIR","divide_id":"0e4a9d06cd3741f088d6c0958ef02f7e","apply_user_id":"aa984e945b844a1a85b0a9d144be3d78","apply_instance_id":"cc713e6692d34327a207dfba021f16dd","district_id":"fcb181d98adc4bd284a0428751347971","audit_type":"INNER_AUDIT_FORCE_CLOSE","status":"submit","initData":{}}
         */

        private WorkorderAuditModelBean workorder_audit_model;

        public WorkorderAuditModelBean getWorkorder_audit_model() {
            return workorder_audit_model;
        }

        public void setWorkorder_audit_model(WorkorderAuditModelBean workorder_audit_model) {
            this.workorder_audit_model = workorder_audit_model;
        }

        public static class WorkorderAuditModelBean {
            /**
             * divide_name : 测试苏南地块
             * audit_code : QZBD20190424100003
             * zone_name : 测试区域
             * project_name : 测试苏南项目
             * apply_biz_id : 2dbefc822d2b4ce9ad9c3901e4bb832a
             * ref_id_ : 0
             * zone_id : 35d6963e94f84558a27e850a374f16c6
             * district_name : 测试江浙沪城区
             * apply_date : 2019-04-24 10:15:24
             * form_data : {"code":"SFSYSFQ001-GC-BX-20190412200006","workOrderCategory":"客户报修工单","applyReason":"好","category":"工程维修类","repairArea":"公区"}
             * project_id : 44766287f99647a49669ece17d52a2f4
             * audit_instance_id : bd881727bb534d1b9e1beb1d169373ea
             * apply_flow_key : customer_repair_flow
             * apply_key_title : SFSYSFQ001-GC-BX-20190412200006
             * callback_status : 0
             * id_ : 50ff4640252e4ed783baa25d54c93607
             * apply_user : 工程员工
             * audit_sub_type : FORCE_CLOSE_REPAIR
             * divide_id : 0e4a9d06cd3741f088d6c0958ef02f7e
             * apply_user_id : aa984e945b844a1a85b0a9d144be3d78
             * apply_instance_id : cc713e6692d34327a207dfba021f16dd
             * district_id : fcb181d98adc4bd284a0428751347971
             * audit_type : INNER_AUDIT_FORCE_CLOSE
             * status : submit
             * initData : {}
             */

            private String divide_name;
            private String audit_code;
            private String zone_name;
            private String project_name;
            private String apply_biz_id;
            private String ref_id_;
            private String zone_id;
            private String district_name;
            private String apply_date;
            private String form_data;
            private String project_id;
            private String audit_instance_id;
            private String apply_flow_key;
            private String apply_key_title;
            private int callback_status;
            private String id_;
            private String apply_user;
            private String audit_sub_type;
            private String divide_id;
            private String apply_user_id;
            private String apply_instance_id;
            private String district_id;
            private String audit_type;
            private String status;
            private InitDataBean initData;

            public String getDivide_name() {
                return divide_name;
            }

            public void setDivide_name(String divide_name) {
                this.divide_name = divide_name;
            }

            public String getAudit_code() {
                return audit_code;
            }

            public void setAudit_code(String audit_code) {
                this.audit_code = audit_code;
            }

            public String getZone_name() {
                return zone_name;
            }

            public void setZone_name(String zone_name) {
                this.zone_name = zone_name;
            }

            public String getProject_name() {
                return project_name;
            }

            public void setProject_name(String project_name) {
                this.project_name = project_name;
            }

            public String getApply_biz_id() {
                return apply_biz_id;
            }

            public void setApply_biz_id(String apply_biz_id) {
                this.apply_biz_id = apply_biz_id;
            }

            public String getRef_id_() {
                return ref_id_;
            }

            public void setRef_id_(String ref_id_) {
                this.ref_id_ = ref_id_;
            }

            public String getZone_id() {
                return zone_id;
            }

            public void setZone_id(String zone_id) {
                this.zone_id = zone_id;
            }

            public String getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }

            public String getApply_date() {
                return apply_date;
            }

            public void setApply_date(String apply_date) {
                this.apply_date = apply_date;
            }

            public String getForm_data() {
                return form_data;
            }

            public void setForm_data(String form_data) {
                this.form_data = form_data;
            }

            public String getProject_id() {
                return project_id;
            }

            public void setProject_id(String project_id) {
                this.project_id = project_id;
            }

            public String getAudit_instance_id() {
                return audit_instance_id;
            }

            public void setAudit_instance_id(String audit_instance_id) {
                this.audit_instance_id = audit_instance_id;
            }

            public String getApply_flow_key() {
                return apply_flow_key;
            }

            public void setApply_flow_key(String apply_flow_key) {
                this.apply_flow_key = apply_flow_key;
            }

            public String getApply_key_title() {
                return apply_key_title;
            }

            public void setApply_key_title(String apply_key_title) {
                this.apply_key_title = apply_key_title;
            }

            public int getCallback_status() {
                return callback_status;
            }

            public void setCallback_status(int callback_status) {
                this.callback_status = callback_status;
            }

            public String getId_() {
                return id_;
            }

            public void setId_(String id_) {
                this.id_ = id_;
            }

            public String getApply_user() {
                return apply_user;
            }

            public void setApply_user(String apply_user) {
                this.apply_user = apply_user;
            }

            public String getAudit_sub_type() {
                return audit_sub_type;
            }

            public void setAudit_sub_type(String audit_sub_type) {
                this.audit_sub_type = audit_sub_type;
            }

            public String getDivide_id() {
                return divide_id;
            }

            public void setDivide_id(String divide_id) {
                this.divide_id = divide_id;
            }

            public String getApply_user_id() {
                return apply_user_id;
            }

            public void setApply_user_id(String apply_user_id) {
                this.apply_user_id = apply_user_id;
            }

            public String getApply_instance_id() {
                return apply_instance_id;
            }

            public void setApply_instance_id(String apply_instance_id) {
                this.apply_instance_id = apply_instance_id;
            }

            public String getDistrict_id() {
                return district_id;
            }

            public void setDistrict_id(String district_id) {
                this.district_id = district_id;
            }

            public String getAudit_type() {
                return audit_type;
            }

            public void setAudit_type(String audit_type) {
                this.audit_type = audit_type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public InitDataBean getInitData() {
                return initData;
            }

            public void setInitData(InitDataBean initData) {
                this.initData = initData;
            }

            public static class InitDataBean {
            }
        }
    }
}
