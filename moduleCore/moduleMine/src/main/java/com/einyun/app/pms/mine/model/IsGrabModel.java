package com.einyun.app.pms.mine.model;

import java.util.List;

public class IsGrabModel {

    /**
     * result : true
     * form : null
     * data : {"customer_repair_model":{"bx_appoint_time_period":"21:00~22:00","fclose_applyer_id":null,"handle_receipt_no":null,"fclose_approve_result":null,"grid_code":null,"housekeeper_name":null,"response_result":null,"return_user":null,"handle_man_hour":null,"return_time":null,"service_unit_fee":null,"bx_cate_lv3_id":"sprinklers","fault_desc":null,"line_name":"工程","service_quality_content":null,"state":"for_grab","fault_area":null,"fclose_approve_time":null,"app_state":"added","service_unit_rate":null,"u_project":"ops-测试项目12","handle_pay_type_id":null,"service_attitude_content":null,"house_code":null,"bx_recorder":"徐玲玲","tenant_id":"55614223698362369","fclose_apply_attach":null,"bx_dk_id":"71105412673306630","line_key":"engineering_classification","bx_build_id":null,"bx_user":"23","bx_time":"2020-03-27 20:11:57","work_ascription":null,"ref_id_":"0","staff_rate":null,"bx_cate_lv1_id":"indoor_repair","assign_grab_state":"3","grab_time":null,"handle_pay_time":null,"u_city_area":"ops-区域01","u_region":"ops-长城物业集团","return_user_id":null,"building_name":null,"bx_unit_id":null,"response_timeout":null,"assign_grab_user":null,"pd_user_id":null,"bx_area_id":"indoor","bx_code":"test005-GC-BX-20200327200003","fclose_apply_reason":null,"return_visit_timeout":null,"bx_property_ass":"一般","return_unsatisfy_do":null,"return_score":"0","return_threshold":null,"return_visit_result":null,"staff_fee":null,"c_is_solve":0,"handle_time":null,"dispatch_close":null,"bx_way":"来访","bx_area":"户内","handle_user":null,"ot_pd_user_id":null,"pd_remark":null,"bx_dk":"test005","proc_inst_id_":"75779454856593414","housekeeper_account":null,"handle_fee":null,"grid_id":null,"return_result":null,"is_grab_overtime":null,"artificial_cost":null,"bx_cate_lv2":"电路","response_user_id":null,"bx_cate_lv1":"户内报修类","bx_property_ass_id":"normal","service_quality_score":"0","bx_cate_lv3":"浴霸","material_cost":null,"project_fee":null,"work_order_timeout":null,"id_":"75779454856594438","receive_user":null,"joint_processor":null,"u_project_id":"71024864856637446","receive_time":null,"close_time":null,"grid_name":null,"unit_name":null,"return_way_id":null,"bx_house_id":null,"bx_appoint_time":"2020-03-27","project_rate":null,"handle_is_paid":null,"bx_way_id":"visit","bx_recorder_seat_number":null,"fclose_applyer":null,"handle_user_id":null,"u_region_id":"63872374214230021","bx_recorder_id":"66929190485622790","handle_pay_type":null,"c_deadline_time":"2020-04-03 20:11:57","bx_mobile":"15623232323","fclose_approve_id":null,"c_return_visit_status":null,"return_way":null,"receive_user_id":null,"return_visit_num":null,"bx_user_id":null,"bx_house":null,"pd_user":null,"ot_pd_user":null,"handle_result":null,"ot_pd_time":null,"bx_content":"23","u_city_area_id":"67114543020507142","handle_timeout":null,"bx_attachment":null,"bx_appoint_time_period_id":"21_22","close_remark":null,"bx_cate_lv2_id":"electrocircuit","pd_time":null,"response_time":null,"response_user":null,"assign_grab_user_id":null,"fclose_apply_time":null,"fclose_is_applying":0,"work_ascription_code":null,"handle_attach":null,"sub_repair_materials":[],"initData":{"repair_materials":{"MAKTX":"","quantity":"","LABST":"","total_price":"","LGORT":"","WERKS":"","LGOBE":"","MEINS":"","NAME1":"","NETPR":"","price":"","name":"","MATKL":"","MATNR":""}}}}
     * opinionList : null
     * permission : null
     * buttons : null
     * info : {"formkey":"","flowKey":"customer_repair_flow","nodeId":"WorkOrderPoolGrab","parentFlowKey":""}
     */

    private boolean result;
    private Object form;
    private DataBean data;
    private Object opinionList;
    private Object permission;
    private Object buttons;
    private InfoBean info;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getForm() {
        return form;
    }

    public void setForm(Object form) {
        this.form = form;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getOpinionList() {
        return opinionList;
    }

    public void setOpinionList(Object opinionList) {
        this.opinionList = opinionList;
    }

    public Object getPermission() {
        return permission;
    }

    public void setPermission(Object permission) {
        this.permission = permission;
    }

    public Object getButtons() {
        return buttons;
    }

    public void setButtons(Object buttons) {
        this.buttons = buttons;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class DataBean {
        /**
         * customer_repair_model : {"bx_appoint_time_period":"21:00~22:00","fclose_applyer_id":null,"handle_receipt_no":null,"fclose_approve_result":null,"grid_code":null,"housekeeper_name":null,"response_result":null,"return_user":null,"handle_man_hour":null,"return_time":null,"service_unit_fee":null,"bx_cate_lv3_id":"sprinklers","fault_desc":null,"line_name":"工程","service_quality_content":null,"state":"for_grab","fault_area":null,"fclose_approve_time":null,"app_state":"added","service_unit_rate":null,"u_project":"ops-测试项目12","handle_pay_type_id":null,"service_attitude_content":null,"house_code":null,"bx_recorder":"徐玲玲","tenant_id":"55614223698362369","fclose_apply_attach":null,"bx_dk_id":"71105412673306630","line_key":"engineering_classification","bx_build_id":null,"bx_user":"23","bx_time":"2020-03-27 20:11:57","work_ascription":null,"ref_id_":"0","staff_rate":null,"bx_cate_lv1_id":"indoor_repair","assign_grab_state":"3","grab_time":null,"handle_pay_time":null,"u_city_area":"ops-区域01","u_region":"ops-长城物业集团","return_user_id":null,"building_name":null,"bx_unit_id":null,"response_timeout":null,"assign_grab_user":null,"pd_user_id":null,"bx_area_id":"indoor","bx_code":"test005-GC-BX-20200327200003","fclose_apply_reason":null,"return_visit_timeout":null,"bx_property_ass":"一般","return_unsatisfy_do":null,"return_score":"0","return_threshold":null,"return_visit_result":null,"staff_fee":null,"c_is_solve":0,"handle_time":null,"dispatch_close":null,"bx_way":"来访","bx_area":"户内","handle_user":null,"ot_pd_user_id":null,"pd_remark":null,"bx_dk":"test005","proc_inst_id_":"75779454856593414","housekeeper_account":null,"handle_fee":null,"grid_id":null,"return_result":null,"is_grab_overtime":null,"artificial_cost":null,"bx_cate_lv2":"电路","response_user_id":null,"bx_cate_lv1":"户内报修类","bx_property_ass_id":"normal","service_quality_score":"0","bx_cate_lv3":"浴霸","material_cost":null,"project_fee":null,"work_order_timeout":null,"id_":"75779454856594438","receive_user":null,"joint_processor":null,"u_project_id":"71024864856637446","receive_time":null,"close_time":null,"grid_name":null,"unit_name":null,"return_way_id":null,"bx_house_id":null,"bx_appoint_time":"2020-03-27","project_rate":null,"handle_is_paid":null,"bx_way_id":"visit","bx_recorder_seat_number":null,"fclose_applyer":null,"handle_user_id":null,"u_region_id":"63872374214230021","bx_recorder_id":"66929190485622790","handle_pay_type":null,"c_deadline_time":"2020-04-03 20:11:57","bx_mobile":"15623232323","fclose_approve_id":null,"c_return_visit_status":null,"return_way":null,"receive_user_id":null,"return_visit_num":null,"bx_user_id":null,"bx_house":null,"pd_user":null,"ot_pd_user":null,"handle_result":null,"ot_pd_time":null,"bx_content":"23","u_city_area_id":"67114543020507142","handle_timeout":null,"bx_attachment":null,"bx_appoint_time_period_id":"21_22","close_remark":null,"bx_cate_lv2_id":"electrocircuit","pd_time":null,"response_time":null,"response_user":null,"assign_grab_user_id":null,"fclose_apply_time":null,"fclose_is_applying":0,"work_ascription_code":null,"handle_attach":null,"sub_repair_materials":[],"initData":{"repair_materials":{"MAKTX":"","quantity":"","LABST":"","total_price":"","LGORT":"","WERKS":"","LGOBE":"","MEINS":"","NAME1":"","NETPR":"","price":"","name":"","MATKL":"","MATNR":""}}}
         */

        private CustomerRepairModelBean customer_repair_model;

        public CustomerRepairModelBean getCustomer_repair_model() {
            return customer_repair_model;
        }

        public void setCustomer_repair_model(CustomerRepairModelBean customer_repair_model) {
            this.customer_repair_model = customer_repair_model;
        }

        public static class CustomerRepairModelBean {
            /**
             * bx_appoint_time_period : 21:00~22:00
             * fclose_applyer_id : null
             * handle_receipt_no : null
             * fclose_approve_result : null
             * grid_code : null
             * housekeeper_name : null
             * response_result : null
             * return_user : null
             * handle_man_hour : null
             * return_time : null
             * service_unit_fee : null
             * bx_cate_lv3_id : sprinklers
             * fault_desc : null
             * line_name : 工程
             * service_quality_content : null
             * state : for_grab
             * fault_area : null
             * fclose_approve_time : null
             * app_state : added
             * service_unit_rate : null
             * u_project : ops-测试项目12
             * handle_pay_type_id : null
             * service_attitude_content : null
             * house_code : null
             * bx_recorder : 徐玲玲
             * tenant_id : 55614223698362369
             * fclose_apply_attach : null
             * bx_dk_id : 71105412673306630
             * line_key : engineering_classification
             * bx_build_id : null
             * bx_user : 23
             * bx_time : 2020-03-27 20:11:57
             * work_ascription : null
             * ref_id_ : 0
             * staff_rate : null
             * bx_cate_lv1_id : indoor_repair
             * assign_grab_state : 3
             * grab_time : null
             * handle_pay_time : null
             * u_city_area : ops-区域01
             * u_region : ops-长城物业集团
             * return_user_id : null
             * building_name : null
             * bx_unit_id : null
             * response_timeout : null
             * assign_grab_user : null
             * pd_user_id : null
             * bx_area_id : indoor
             * bx_code : test005-GC-BX-20200327200003
             * fclose_apply_reason : null
             * return_visit_timeout : null
             * bx_property_ass : 一般
             * return_unsatisfy_do : null
             * return_score : 0
             * return_threshold : null
             * return_visit_result : null
             * staff_fee : null
             * c_is_solve : 0
             * handle_time : null
             * dispatch_close : null
             * bx_way : 来访
             * bx_area : 户内
             * handle_user : null
             * ot_pd_user_id : null
             * pd_remark : null
             * bx_dk : test005
             * proc_inst_id_ : 75779454856593414
             * housekeeper_account : null
             * handle_fee : null
             * grid_id : null
             * return_result : null
             * is_grab_overtime : null
             * artificial_cost : null
             * bx_cate_lv2 : 电路
             * response_user_id : null
             * bx_cate_lv1 : 户内报修类
             * bx_property_ass_id : normal
             * service_quality_score : 0
             * bx_cate_lv3 : 浴霸
             * material_cost : null
             * project_fee : null
             * work_order_timeout : null
             * id_ : 75779454856594438
             * receive_user : null
             * joint_processor : null
             * u_project_id : 71024864856637446
             * receive_time : null
             * close_time : null
             * grid_name : null
             * unit_name : null
             * return_way_id : null
             * bx_house_id : null
             * bx_appoint_time : 2020-03-27
             * project_rate : null
             * handle_is_paid : null
             * bx_way_id : visit
             * bx_recorder_seat_number : null
             * fclose_applyer : null
             * handle_user_id : null
             * u_region_id : 63872374214230021
             * bx_recorder_id : 66929190485622790
             * handle_pay_type : null
             * c_deadline_time : 2020-04-03 20:11:57
             * bx_mobile : 15623232323
             * fclose_approve_id : null
             * c_return_visit_status : null
             * return_way : null
             * receive_user_id : null
             * return_visit_num : null
             * bx_user_id : null
             * bx_house : null
             * pd_user : null
             * ot_pd_user : null
             * handle_result : null
             * ot_pd_time : null
             * bx_content : 23
             * u_city_area_id : 67114543020507142
             * handle_timeout : null
             * bx_attachment : null
             * bx_appoint_time_period_id : 21_22
             * close_remark : null
             * bx_cate_lv2_id : electrocircuit
             * pd_time : null
             * response_time : null
             * response_user : null
             * assign_grab_user_id : null
             * fclose_apply_time : null
             * fclose_is_applying : 0
             * work_ascription_code : null
             * handle_attach : null
             * sub_repair_materials : []
             * initData : {"repair_materials":{"MAKTX":"","quantity":"","LABST":"","total_price":"","LGORT":"","WERKS":"","LGOBE":"","MEINS":"","NAME1":"","NETPR":"","price":"","name":"","MATKL":"","MATNR":""}}
             */

            private String bx_appoint_time_period;
            private Object fclose_applyer_id;
            private Object handle_receipt_no;
            private Object fclose_approve_result;
            private Object grid_code;
            private Object housekeeper_name;
            private Object response_result;
            private Object return_user;
            private Object handle_man_hour;
            private Object return_time;
            private Object service_unit_fee;
            private String bx_cate_lv3_id;
            private Object fault_desc;
            private String line_name;
            private Object service_quality_content;
            private String state;
            private Object fault_area;
            private Object fclose_approve_time;
            private String app_state;
            private Object service_unit_rate;
            private String u_project;
            private Object handle_pay_type_id;
            private Object service_attitude_content;
            private Object house_code;
            private String bx_recorder;
            private String tenant_id;
            private Object fclose_apply_attach;
            private String bx_dk_id;
            private String line_key;
            private Object bx_build_id;
            private String bx_user;
            private String bx_time;
            private Object work_ascription;
            private String ref_id_;
            private Object staff_rate;
            private String bx_cate_lv1_id;
            private String assign_grab_state;
            private Object grab_time;
            private Object handle_pay_time;
            private String u_city_area;
            private String u_region;
            private Object return_user_id;
            private Object building_name;
            private Object bx_unit_id;
            private Object response_timeout;
            private Object assign_grab_user;
            private Object pd_user_id;
            private String bx_area_id;
            private String bx_code;
            private Object fclose_apply_reason;
            private Object return_visit_timeout;
            private String bx_property_ass;
            private Object return_unsatisfy_do;
            private String return_score;
            private Object return_threshold;
            private Object return_visit_result;
            private Object staff_fee;
            private int c_is_solve;
            private Object handle_time;
            private Object dispatch_close;
            private String bx_way;
            private String bx_area;
            private Object handle_user;
            private Object ot_pd_user_id;
            private Object pd_remark;
            private String bx_dk;
            private String proc_inst_id_;
            private Object housekeeper_account;
            private Object handle_fee;
            private Object grid_id;
            private Object return_result;
            private Object is_grab_overtime;
            private Object artificial_cost;
            private String bx_cate_lv2;
            private Object response_user_id;
            private String bx_cate_lv1;
            private String bx_property_ass_id;
            private String service_quality_score;
            private String bx_cate_lv3;
            private Object material_cost;
            private Object project_fee;
            private Object work_order_timeout;
            private String id_;
            private Object receive_user;
            private Object joint_processor;
            private String u_project_id;
            private Object receive_time;
            private Object close_time;
            private Object grid_name;
            private Object unit_name;
            private Object return_way_id;
            private Object bx_house_id;
            private String bx_appoint_time;
            private Object project_rate;
            private Object handle_is_paid;
            private String bx_way_id;
            private Object bx_recorder_seat_number;
            private Object fclose_applyer;
            private Object handle_user_id;
            private String u_region_id;
            private String bx_recorder_id;
            private Object handle_pay_type;
            private String c_deadline_time;
            private String bx_mobile;
            private Object fclose_approve_id;
            private Object c_return_visit_status;
            private Object return_way;
            private Object receive_user_id;
            private Object return_visit_num;
            private Object bx_user_id;
            private Object bx_house;
            private Object pd_user;
            private Object ot_pd_user;
            private Object handle_result;
            private Object ot_pd_time;
            private String bx_content;
            private String u_city_area_id;
            private Object handle_timeout;
            private Object bx_attachment;
            private String bx_appoint_time_period_id;
            private Object close_remark;
            private String bx_cate_lv2_id;
            private Object pd_time;
            private Object response_time;
            private Object response_user;
            private Object assign_grab_user_id;
            private Object fclose_apply_time;
            private int fclose_is_applying;
            private Object work_ascription_code;
            private Object handle_attach;
            private InitDataBean initData;
            private List<?> sub_repair_materials;

            public String getBx_appoint_time_period() {
                return bx_appoint_time_period;
            }

            public void setBx_appoint_time_period(String bx_appoint_time_period) {
                this.bx_appoint_time_period = bx_appoint_time_period;
            }

            public Object getFclose_applyer_id() {
                return fclose_applyer_id;
            }

            public void setFclose_applyer_id(Object fclose_applyer_id) {
                this.fclose_applyer_id = fclose_applyer_id;
            }

            public Object getHandle_receipt_no() {
                return handle_receipt_no;
            }

            public void setHandle_receipt_no(Object handle_receipt_no) {
                this.handle_receipt_no = handle_receipt_no;
            }

            public Object getFclose_approve_result() {
                return fclose_approve_result;
            }

            public void setFclose_approve_result(Object fclose_approve_result) {
                this.fclose_approve_result = fclose_approve_result;
            }

            public Object getGrid_code() {
                return grid_code;
            }

            public void setGrid_code(Object grid_code) {
                this.grid_code = grid_code;
            }

            public Object getHousekeeper_name() {
                return housekeeper_name;
            }

            public void setHousekeeper_name(Object housekeeper_name) {
                this.housekeeper_name = housekeeper_name;
            }

            public Object getResponse_result() {
                return response_result;
            }

            public void setResponse_result(Object response_result) {
                this.response_result = response_result;
            }

            public Object getReturn_user() {
                return return_user;
            }

            public void setReturn_user(Object return_user) {
                this.return_user = return_user;
            }

            public Object getHandle_man_hour() {
                return handle_man_hour;
            }

            public void setHandle_man_hour(Object handle_man_hour) {
                this.handle_man_hour = handle_man_hour;
            }

            public Object getReturn_time() {
                return return_time;
            }

            public void setReturn_time(Object return_time) {
                this.return_time = return_time;
            }

            public Object getService_unit_fee() {
                return service_unit_fee;
            }

            public void setService_unit_fee(Object service_unit_fee) {
                this.service_unit_fee = service_unit_fee;
            }

            public String getBx_cate_lv3_id() {
                return bx_cate_lv3_id;
            }

            public void setBx_cate_lv3_id(String bx_cate_lv3_id) {
                this.bx_cate_lv3_id = bx_cate_lv3_id;
            }

            public Object getFault_desc() {
                return fault_desc;
            }

            public void setFault_desc(Object fault_desc) {
                this.fault_desc = fault_desc;
            }

            public String getLine_name() {
                return line_name;
            }

            public void setLine_name(String line_name) {
                this.line_name = line_name;
            }

            public Object getService_quality_content() {
                return service_quality_content;
            }

            public void setService_quality_content(Object service_quality_content) {
                this.service_quality_content = service_quality_content;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public Object getFault_area() {
                return fault_area;
            }

            public void setFault_area(Object fault_area) {
                this.fault_area = fault_area;
            }

            public Object getFclose_approve_time() {
                return fclose_approve_time;
            }

            public void setFclose_approve_time(Object fclose_approve_time) {
                this.fclose_approve_time = fclose_approve_time;
            }

            public String getApp_state() {
                return app_state;
            }

            public void setApp_state(String app_state) {
                this.app_state = app_state;
            }

            public Object getService_unit_rate() {
                return service_unit_rate;
            }

            public void setService_unit_rate(Object service_unit_rate) {
                this.service_unit_rate = service_unit_rate;
            }

            public String getU_project() {
                return u_project;
            }

            public void setU_project(String u_project) {
                this.u_project = u_project;
            }

            public Object getHandle_pay_type_id() {
                return handle_pay_type_id;
            }

            public void setHandle_pay_type_id(Object handle_pay_type_id) {
                this.handle_pay_type_id = handle_pay_type_id;
            }

            public Object getService_attitude_content() {
                return service_attitude_content;
            }

            public void setService_attitude_content(Object service_attitude_content) {
                this.service_attitude_content = service_attitude_content;
            }

            public Object getHouse_code() {
                return house_code;
            }

            public void setHouse_code(Object house_code) {
                this.house_code = house_code;
            }

            public String getBx_recorder() {
                return bx_recorder;
            }

            public void setBx_recorder(String bx_recorder) {
                this.bx_recorder = bx_recorder;
            }

            public String getTenant_id() {
                return tenant_id;
            }

            public void setTenant_id(String tenant_id) {
                this.tenant_id = tenant_id;
            }

            public Object getFclose_apply_attach() {
                return fclose_apply_attach;
            }

            public void setFclose_apply_attach(Object fclose_apply_attach) {
                this.fclose_apply_attach = fclose_apply_attach;
            }

            public String getBx_dk_id() {
                return bx_dk_id;
            }

            public void setBx_dk_id(String bx_dk_id) {
                this.bx_dk_id = bx_dk_id;
            }

            public String getLine_key() {
                return line_key;
            }

            public void setLine_key(String line_key) {
                this.line_key = line_key;
            }

            public Object getBx_build_id() {
                return bx_build_id;
            }

            public void setBx_build_id(Object bx_build_id) {
                this.bx_build_id = bx_build_id;
            }

            public String getBx_user() {
                return bx_user;
            }

            public void setBx_user(String bx_user) {
                this.bx_user = bx_user;
            }

            public String getBx_time() {
                return bx_time;
            }

            public void setBx_time(String bx_time) {
                this.bx_time = bx_time;
            }

            public Object getWork_ascription() {
                return work_ascription;
            }

            public void setWork_ascription(Object work_ascription) {
                this.work_ascription = work_ascription;
            }

            public String getRef_id_() {
                return ref_id_;
            }

            public void setRef_id_(String ref_id_) {
                this.ref_id_ = ref_id_;
            }

            public Object getStaff_rate() {
                return staff_rate;
            }

            public void setStaff_rate(Object staff_rate) {
                this.staff_rate = staff_rate;
            }

            public String getBx_cate_lv1_id() {
                return bx_cate_lv1_id;
            }

            public void setBx_cate_lv1_id(String bx_cate_lv1_id) {
                this.bx_cate_lv1_id = bx_cate_lv1_id;
            }

            public String getAssign_grab_state() {
                return assign_grab_state;
            }

            public void setAssign_grab_state(String assign_grab_state) {
                this.assign_grab_state = assign_grab_state;
            }

            public Object getGrab_time() {
                return grab_time;
            }

            public void setGrab_time(Object grab_time) {
                this.grab_time = grab_time;
            }

            public Object getHandle_pay_time() {
                return handle_pay_time;
            }

            public void setHandle_pay_time(Object handle_pay_time) {
                this.handle_pay_time = handle_pay_time;
            }

            public String getU_city_area() {
                return u_city_area;
            }

            public void setU_city_area(String u_city_area) {
                this.u_city_area = u_city_area;
            }

            public String getU_region() {
                return u_region;
            }

            public void setU_region(String u_region) {
                this.u_region = u_region;
            }

            public Object getReturn_user_id() {
                return return_user_id;
            }

            public void setReturn_user_id(Object return_user_id) {
                this.return_user_id = return_user_id;
            }

            public Object getBuilding_name() {
                return building_name;
            }

            public void setBuilding_name(Object building_name) {
                this.building_name = building_name;
            }

            public Object getBx_unit_id() {
                return bx_unit_id;
            }

            public void setBx_unit_id(Object bx_unit_id) {
                this.bx_unit_id = bx_unit_id;
            }

            public Object getResponse_timeout() {
                return response_timeout;
            }

            public void setResponse_timeout(Object response_timeout) {
                this.response_timeout = response_timeout;
            }

            public Object getAssign_grab_user() {
                return assign_grab_user;
            }

            public void setAssign_grab_user(Object assign_grab_user) {
                this.assign_grab_user = assign_grab_user;
            }

            public Object getPd_user_id() {
                return pd_user_id;
            }

            public void setPd_user_id(Object pd_user_id) {
                this.pd_user_id = pd_user_id;
            }

            public String getBx_area_id() {
                return bx_area_id;
            }

            public void setBx_area_id(String bx_area_id) {
                this.bx_area_id = bx_area_id;
            }

            public String getBx_code() {
                return bx_code;
            }

            public void setBx_code(String bx_code) {
                this.bx_code = bx_code;
            }

            public Object getFclose_apply_reason() {
                return fclose_apply_reason;
            }

            public void setFclose_apply_reason(Object fclose_apply_reason) {
                this.fclose_apply_reason = fclose_apply_reason;
            }

            public Object getReturn_visit_timeout() {
                return return_visit_timeout;
            }

            public void setReturn_visit_timeout(Object return_visit_timeout) {
                this.return_visit_timeout = return_visit_timeout;
            }

            public String getBx_property_ass() {
                return bx_property_ass;
            }

            public void setBx_property_ass(String bx_property_ass) {
                this.bx_property_ass = bx_property_ass;
            }

            public Object getReturn_unsatisfy_do() {
                return return_unsatisfy_do;
            }

            public void setReturn_unsatisfy_do(Object return_unsatisfy_do) {
                this.return_unsatisfy_do = return_unsatisfy_do;
            }

            public String getReturn_score() {
                return return_score;
            }

            public void setReturn_score(String return_score) {
                this.return_score = return_score;
            }

            public Object getReturn_threshold() {
                return return_threshold;
            }

            public void setReturn_threshold(Object return_threshold) {
                this.return_threshold = return_threshold;
            }

            public Object getReturn_visit_result() {
                return return_visit_result;
            }

            public void setReturn_visit_result(Object return_visit_result) {
                this.return_visit_result = return_visit_result;
            }

            public Object getStaff_fee() {
                return staff_fee;
            }

            public void setStaff_fee(Object staff_fee) {
                this.staff_fee = staff_fee;
            }

            public int getC_is_solve() {
                return c_is_solve;
            }

            public void setC_is_solve(int c_is_solve) {
                this.c_is_solve = c_is_solve;
            }

            public Object getHandle_time() {
                return handle_time;
            }

            public void setHandle_time(Object handle_time) {
                this.handle_time = handle_time;
            }

            public Object getDispatch_close() {
                return dispatch_close;
            }

            public void setDispatch_close(Object dispatch_close) {
                this.dispatch_close = dispatch_close;
            }

            public String getBx_way() {
                return bx_way;
            }

            public void setBx_way(String bx_way) {
                this.bx_way = bx_way;
            }

            public String getBx_area() {
                return bx_area;
            }

            public void setBx_area(String bx_area) {
                this.bx_area = bx_area;
            }

            public Object getHandle_user() {
                return handle_user;
            }

            public void setHandle_user(Object handle_user) {
                this.handle_user = handle_user;
            }

            public Object getOt_pd_user_id() {
                return ot_pd_user_id;
            }

            public void setOt_pd_user_id(Object ot_pd_user_id) {
                this.ot_pd_user_id = ot_pd_user_id;
            }

            public Object getPd_remark() {
                return pd_remark;
            }

            public void setPd_remark(Object pd_remark) {
                this.pd_remark = pd_remark;
            }

            public String getBx_dk() {
                return bx_dk;
            }

            public void setBx_dk(String bx_dk) {
                this.bx_dk = bx_dk;
            }

            public String getProc_inst_id_() {
                return proc_inst_id_;
            }

            public void setProc_inst_id_(String proc_inst_id_) {
                this.proc_inst_id_ = proc_inst_id_;
            }

            public Object getHousekeeper_account() {
                return housekeeper_account;
            }

            public void setHousekeeper_account(Object housekeeper_account) {
                this.housekeeper_account = housekeeper_account;
            }

            public Object getHandle_fee() {
                return handle_fee;
            }

            public void setHandle_fee(Object handle_fee) {
                this.handle_fee = handle_fee;
            }

            public Object getGrid_id() {
                return grid_id;
            }

            public void setGrid_id(Object grid_id) {
                this.grid_id = grid_id;
            }

            public Object getReturn_result() {
                return return_result;
            }

            public void setReturn_result(Object return_result) {
                this.return_result = return_result;
            }

            public Object getIs_grab_overtime() {
                return is_grab_overtime;
            }

            public void setIs_grab_overtime(Object is_grab_overtime) {
                this.is_grab_overtime = is_grab_overtime;
            }

            public Object getArtificial_cost() {
                return artificial_cost;
            }

            public void setArtificial_cost(Object artificial_cost) {
                this.artificial_cost = artificial_cost;
            }

            public String getBx_cate_lv2() {
                return bx_cate_lv2;
            }

            public void setBx_cate_lv2(String bx_cate_lv2) {
                this.bx_cate_lv2 = bx_cate_lv2;
            }

            public Object getResponse_user_id() {
                return response_user_id;
            }

            public void setResponse_user_id(Object response_user_id) {
                this.response_user_id = response_user_id;
            }

            public String getBx_cate_lv1() {
                return bx_cate_lv1;
            }

            public void setBx_cate_lv1(String bx_cate_lv1) {
                this.bx_cate_lv1 = bx_cate_lv1;
            }

            public String getBx_property_ass_id() {
                return bx_property_ass_id;
            }

            public void setBx_property_ass_id(String bx_property_ass_id) {
                this.bx_property_ass_id = bx_property_ass_id;
            }

            public String getService_quality_score() {
                return service_quality_score;
            }

            public void setService_quality_score(String service_quality_score) {
                this.service_quality_score = service_quality_score;
            }

            public String getBx_cate_lv3() {
                return bx_cate_lv3;
            }

            public void setBx_cate_lv3(String bx_cate_lv3) {
                this.bx_cate_lv3 = bx_cate_lv3;
            }

            public Object getMaterial_cost() {
                return material_cost;
            }

            public void setMaterial_cost(Object material_cost) {
                this.material_cost = material_cost;
            }

            public Object getProject_fee() {
                return project_fee;
            }

            public void setProject_fee(Object project_fee) {
                this.project_fee = project_fee;
            }

            public Object getWork_order_timeout() {
                return work_order_timeout;
            }

            public void setWork_order_timeout(Object work_order_timeout) {
                this.work_order_timeout = work_order_timeout;
            }

            public String getId_() {
                return id_;
            }

            public void setId_(String id_) {
                this.id_ = id_;
            }

            public Object getReceive_user() {
                return receive_user;
            }

            public void setReceive_user(Object receive_user) {
                this.receive_user = receive_user;
            }

            public Object getJoint_processor() {
                return joint_processor;
            }

            public void setJoint_processor(Object joint_processor) {
                this.joint_processor = joint_processor;
            }

            public String getU_project_id() {
                return u_project_id;
            }

            public void setU_project_id(String u_project_id) {
                this.u_project_id = u_project_id;
            }

            public Object getReceive_time() {
                return receive_time;
            }

            public void setReceive_time(Object receive_time) {
                this.receive_time = receive_time;
            }

            public Object getClose_time() {
                return close_time;
            }

            public void setClose_time(Object close_time) {
                this.close_time = close_time;
            }

            public Object getGrid_name() {
                return grid_name;
            }

            public void setGrid_name(Object grid_name) {
                this.grid_name = grid_name;
            }

            public Object getUnit_name() {
                return unit_name;
            }

            public void setUnit_name(Object unit_name) {
                this.unit_name = unit_name;
            }

            public Object getReturn_way_id() {
                return return_way_id;
            }

            public void setReturn_way_id(Object return_way_id) {
                this.return_way_id = return_way_id;
            }

            public Object getBx_house_id() {
                return bx_house_id;
            }

            public void setBx_house_id(Object bx_house_id) {
                this.bx_house_id = bx_house_id;
            }

            public String getBx_appoint_time() {
                return bx_appoint_time;
            }

            public void setBx_appoint_time(String bx_appoint_time) {
                this.bx_appoint_time = bx_appoint_time;
            }

            public Object getProject_rate() {
                return project_rate;
            }

            public void setProject_rate(Object project_rate) {
                this.project_rate = project_rate;
            }

            public Object getHandle_is_paid() {
                return handle_is_paid;
            }

            public void setHandle_is_paid(Object handle_is_paid) {
                this.handle_is_paid = handle_is_paid;
            }

            public String getBx_way_id() {
                return bx_way_id;
            }

            public void setBx_way_id(String bx_way_id) {
                this.bx_way_id = bx_way_id;
            }

            public Object getBx_recorder_seat_number() {
                return bx_recorder_seat_number;
            }

            public void setBx_recorder_seat_number(Object bx_recorder_seat_number) {
                this.bx_recorder_seat_number = bx_recorder_seat_number;
            }

            public Object getFclose_applyer() {
                return fclose_applyer;
            }

            public void setFclose_applyer(Object fclose_applyer) {
                this.fclose_applyer = fclose_applyer;
            }

            public Object getHandle_user_id() {
                return handle_user_id;
            }

            public void setHandle_user_id(Object handle_user_id) {
                this.handle_user_id = handle_user_id;
            }

            public String getU_region_id() {
                return u_region_id;
            }

            public void setU_region_id(String u_region_id) {
                this.u_region_id = u_region_id;
            }

            public String getBx_recorder_id() {
                return bx_recorder_id;
            }

            public void setBx_recorder_id(String bx_recorder_id) {
                this.bx_recorder_id = bx_recorder_id;
            }

            public Object getHandle_pay_type() {
                return handle_pay_type;
            }

            public void setHandle_pay_type(Object handle_pay_type) {
                this.handle_pay_type = handle_pay_type;
            }

            public String getC_deadline_time() {
                return c_deadline_time;
            }

            public void setC_deadline_time(String c_deadline_time) {
                this.c_deadline_time = c_deadline_time;
            }

            public String getBx_mobile() {
                return bx_mobile;
            }

            public void setBx_mobile(String bx_mobile) {
                this.bx_mobile = bx_mobile;
            }

            public Object getFclose_approve_id() {
                return fclose_approve_id;
            }

            public void setFclose_approve_id(Object fclose_approve_id) {
                this.fclose_approve_id = fclose_approve_id;
            }

            public Object getC_return_visit_status() {
                return c_return_visit_status;
            }

            public void setC_return_visit_status(Object c_return_visit_status) {
                this.c_return_visit_status = c_return_visit_status;
            }

            public Object getReturn_way() {
                return return_way;
            }

            public void setReturn_way(Object return_way) {
                this.return_way = return_way;
            }

            public Object getReceive_user_id() {
                return receive_user_id;
            }

            public void setReceive_user_id(Object receive_user_id) {
                this.receive_user_id = receive_user_id;
            }

            public Object getReturn_visit_num() {
                return return_visit_num;
            }

            public void setReturn_visit_num(Object return_visit_num) {
                this.return_visit_num = return_visit_num;
            }

            public Object getBx_user_id() {
                return bx_user_id;
            }

            public void setBx_user_id(Object bx_user_id) {
                this.bx_user_id = bx_user_id;
            }

            public Object getBx_house() {
                return bx_house;
            }

            public void setBx_house(Object bx_house) {
                this.bx_house = bx_house;
            }

            public Object getPd_user() {
                return pd_user;
            }

            public void setPd_user(Object pd_user) {
                this.pd_user = pd_user;
            }

            public Object getOt_pd_user() {
                return ot_pd_user;
            }

            public void setOt_pd_user(Object ot_pd_user) {
                this.ot_pd_user = ot_pd_user;
            }

            public Object getHandle_result() {
                return handle_result;
            }

            public void setHandle_result(Object handle_result) {
                this.handle_result = handle_result;
            }

            public Object getOt_pd_time() {
                return ot_pd_time;
            }

            public void setOt_pd_time(Object ot_pd_time) {
                this.ot_pd_time = ot_pd_time;
            }

            public String getBx_content() {
                return bx_content;
            }

            public void setBx_content(String bx_content) {
                this.bx_content = bx_content;
            }

            public String getU_city_area_id() {
                return u_city_area_id;
            }

            public void setU_city_area_id(String u_city_area_id) {
                this.u_city_area_id = u_city_area_id;
            }

            public Object getHandle_timeout() {
                return handle_timeout;
            }

            public void setHandle_timeout(Object handle_timeout) {
                this.handle_timeout = handle_timeout;
            }

            public Object getBx_attachment() {
                return bx_attachment;
            }

            public void setBx_attachment(Object bx_attachment) {
                this.bx_attachment = bx_attachment;
            }

            public String getBx_appoint_time_period_id() {
                return bx_appoint_time_period_id;
            }

            public void setBx_appoint_time_period_id(String bx_appoint_time_period_id) {
                this.bx_appoint_time_period_id = bx_appoint_time_period_id;
            }

            public Object getClose_remark() {
                return close_remark;
            }

            public void setClose_remark(Object close_remark) {
                this.close_remark = close_remark;
            }

            public String getBx_cate_lv2_id() {
                return bx_cate_lv2_id;
            }

            public void setBx_cate_lv2_id(String bx_cate_lv2_id) {
                this.bx_cate_lv2_id = bx_cate_lv2_id;
            }

            public Object getPd_time() {
                return pd_time;
            }

            public void setPd_time(Object pd_time) {
                this.pd_time = pd_time;
            }

            public Object getResponse_time() {
                return response_time;
            }

            public void setResponse_time(Object response_time) {
                this.response_time = response_time;
            }

            public Object getResponse_user() {
                return response_user;
            }

            public void setResponse_user(Object response_user) {
                this.response_user = response_user;
            }

            public Object getAssign_grab_user_id() {
                return assign_grab_user_id;
            }

            public void setAssign_grab_user_id(Object assign_grab_user_id) {
                this.assign_grab_user_id = assign_grab_user_id;
            }

            public Object getFclose_apply_time() {
                return fclose_apply_time;
            }

            public void setFclose_apply_time(Object fclose_apply_time) {
                this.fclose_apply_time = fclose_apply_time;
            }

            public int getFclose_is_applying() {
                return fclose_is_applying;
            }

            public void setFclose_is_applying(int fclose_is_applying) {
                this.fclose_is_applying = fclose_is_applying;
            }

            public Object getWork_ascription_code() {
                return work_ascription_code;
            }

            public void setWork_ascription_code(Object work_ascription_code) {
                this.work_ascription_code = work_ascription_code;
            }

            public Object getHandle_attach() {
                return handle_attach;
            }

            public void setHandle_attach(Object handle_attach) {
                this.handle_attach = handle_attach;
            }

            public InitDataBean getInitData() {
                return initData;
            }

            public void setInitData(InitDataBean initData) {
                this.initData = initData;
            }

            public List<?> getSub_repair_materials() {
                return sub_repair_materials;
            }

            public void setSub_repair_materials(List<?> sub_repair_materials) {
                this.sub_repair_materials = sub_repair_materials;
            }

            public static class InitDataBean {
                /**
                 * repair_materials : {"MAKTX":"","quantity":"","LABST":"","total_price":"","LGORT":"","WERKS":"","LGOBE":"","MEINS":"","NAME1":"","NETPR":"","price":"","name":"","MATKL":"","MATNR":""}
                 */

                private RepairMaterialsBean repair_materials;

                public RepairMaterialsBean getRepair_materials() {
                    return repair_materials;
                }

                public void setRepair_materials(RepairMaterialsBean repair_materials) {
                    this.repair_materials = repair_materials;
                }

                public static class RepairMaterialsBean {
                    /**
                     * MAKTX :
                     * quantity :
                     * LABST :
                     * total_price :
                     * LGORT :
                     * WERKS :
                     * LGOBE :
                     * MEINS :
                     * NAME1 :
                     * NETPR :
                     * price :
                     * name :
                     * MATKL :
                     * MATNR :
                     */

                    private String MAKTX;
                    private String quantity;
                    private String LABST;
                    private String total_price;
                    private String LGORT;
                    private String WERKS;
                    private String LGOBE;
                    private String MEINS;
                    private String NAME1;
                    private String NETPR;
                    private String price;
                    private String name;
                    private String MATKL;
                    private String MATNR;

                    public String getMAKTX() {
                        return MAKTX;
                    }

                    public void setMAKTX(String MAKTX) {
                        this.MAKTX = MAKTX;
                    }

                    public String getQuantity() {
                        return quantity;
                    }

                    public void setQuantity(String quantity) {
                        this.quantity = quantity;
                    }

                    public String getLABST() {
                        return LABST;
                    }

                    public void setLABST(String LABST) {
                        this.LABST = LABST;
                    }

                    public String getTotal_price() {
                        return total_price;
                    }

                    public void setTotal_price(String total_price) {
                        this.total_price = total_price;
                    }

                    public String getLGORT() {
                        return LGORT;
                    }

                    public void setLGORT(String LGORT) {
                        this.LGORT = LGORT;
                    }

                    public String getWERKS() {
                        return WERKS;
                    }

                    public void setWERKS(String WERKS) {
                        this.WERKS = WERKS;
                    }

                    public String getLGOBE() {
                        return LGOBE;
                    }

                    public void setLGOBE(String LGOBE) {
                        this.LGOBE = LGOBE;
                    }

                    public String getMEINS() {
                        return MEINS;
                    }

                    public void setMEINS(String MEINS) {
                        this.MEINS = MEINS;
                    }

                    public String getNAME1() {
                        return NAME1;
                    }

                    public void setNAME1(String NAME1) {
                        this.NAME1 = NAME1;
                    }

                    public String getNETPR() {
                        return NETPR;
                    }

                    public void setNETPR(String NETPR) {
                        this.NETPR = NETPR;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getMATKL() {
                        return MATKL;
                    }

                    public void setMATKL(String MATKL) {
                        this.MATKL = MATKL;
                    }

                    public String getMATNR() {
                        return MATNR;
                    }

                    public void setMATNR(String MATNR) {
                        this.MATNR = MATNR;
                    }
                }
            }
        }
    }

    public static class InfoBean {
        /**
         * formkey :
         * flowKey : customer_repair_flow
         * nodeId : WorkOrderPoolGrab
         * parentFlowKey :
         */

        private String formkey;
        private String flowKey;
        private String nodeId;
        private String parentFlowKey;

        public String getFormkey() {
            return formkey;
        }

        public void setFormkey(String formkey) {
            this.formkey = formkey;
        }

        public String getFlowKey() {
            return flowKey;
        }

        public void setFlowKey(String flowKey) {
            this.flowKey = flowKey;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getParentFlowKey() {
            return parentFlowKey;
        }

        public void setParentFlowKey(String parentFlowKey) {
            this.parentFlowKey = parentFlowKey;
        }
    }
}
