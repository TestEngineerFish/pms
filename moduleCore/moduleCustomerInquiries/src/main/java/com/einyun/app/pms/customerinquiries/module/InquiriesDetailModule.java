package com.einyun.app.pms.customerinquiries.module;

public class InquiriesDetailModule {

    /**
     * result : true
     * form : null
     * data : {"customer_enquiry_model":{"handle_time":"2019-12-18 10:41:02","dispatch_close":null,"fclose_applyer_id":null,"handle_user":"越","fclose_approve_result":null,"grid_code":null,"housekeeper_name":null,"proc_inst_id_":"66465331870171140","return_user":null,"wx_dk_id":"63872495547056133","housekeeper_account":null,"wx_recorder_id":"65260234913873925","handle_cont":"回复内容 *:提交","grid_id":null,"return_time":null,"return_result":null,"line_name":"环境","service_quality_content":null,"state":"return_visit","service_quality_score":null,"fclose_approve_time":null,"work_order_timeout":null,"wx_recorder":"胡文越","wx_recorder_seat_number":null,"app_state":null,"wx_house_id":null,"id_":"66465331870172164","u_project_id":"63872468703510533","u_project":"长城盛世家园分公司","close_time":null,"grid_name":null,"unit_name":null,"return_way_id":null,"wx_user_id":null,"service_attitude_content":null,"house_code":null,"wx_code":"ccssjyyq-HJ-WX-20191218100002","tenant_id":"55614223698362369","fclose_apply_attach":null,"wx_attachment":null,"fclose_applyer":null,"wx_cate_id":"environment_virescence","wx_direct_reply":null,"handle_user_id":"65260234913873925","u_region_id":null,"wx_way":"来电","line_key":"environmental_classification","c_deadline_time":"2019-12-25 10:37:43","ref_id_":"0","fclose_approve_id":null,"wx_user":"121","c_return_visit_status":null,"wx_mobile":"13111111111","wx_time":"2019-12-18 10:37:43","u_city_area":null,"wx_cate":"环境绿化类","return_way":null,"return_visit_num":null,"u_region":null,"return_user_id":null,"wx_dk":"长城盛世家园一期","building_name":null,"response_timeout":null,"wx_house":null,"wx_way_id":"call_in","wx_content":"121","fclose_apply_reason":null,"wx_unit_id":null,"u_city_area_id":null,"return_visit_timeout":null,"handle_timeout":null,"wx_process_mode":null,"wx_build_id":null,"return_unsatisfy_do":null,"close_remark":null,"return_score":null,"return_threshold":null,"return_visit_result":null,"fclose_apply_time":null,"fclose_is_applying":0,"c_is_solve":0,"initData":{}}}
     * opinionList : null
     * permission : null
     * buttons : null
     * info : {"formkey":"","flowKey":"customer_enquiry_flow","nodeId":"ReturnVisit","parentFlowKey":""}
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
         * customer_enquiry_model : {"handle_time":"2019-12-18 10:41:02","dispatch_close":null,"fclose_applyer_id":null,"handle_user":"越","fclose_approve_result":null,"grid_code":null,"housekeeper_name":null,"proc_inst_id_":"66465331870171140","return_user":null,"wx_dk_id":"63872495547056133","housekeeper_account":null,"wx_recorder_id":"65260234913873925","handle_cont":"回复内容 *:提交","grid_id":null,"return_time":null,"return_result":null,"line_name":"环境","service_quality_content":null,"state":"return_visit","service_quality_score":null,"fclose_approve_time":null,"work_order_timeout":null,"wx_recorder":"胡文越","wx_recorder_seat_number":null,"app_state":null,"wx_house_id":null,"id_":"66465331870172164","u_project_id":"63872468703510533","u_project":"长城盛世家园分公司","close_time":null,"grid_name":null,"unit_name":null,"return_way_id":null,"wx_user_id":null,"service_attitude_content":null,"house_code":null,"wx_code":"ccssjyyq-HJ-WX-20191218100002","tenant_id":"55614223698362369","fclose_apply_attach":null,"wx_attachment":null,"fclose_applyer":null,"wx_cate_id":"environment_virescence","wx_direct_reply":null,"handle_user_id":"65260234913873925","u_region_id":null,"wx_way":"来电","line_key":"environmental_classification","c_deadline_time":"2019-12-25 10:37:43","ref_id_":"0","fclose_approve_id":null,"wx_user":"121","c_return_visit_status":null,"wx_mobile":"13111111111","wx_time":"2019-12-18 10:37:43","u_city_area":null,"wx_cate":"环境绿化类","return_way":null,"return_visit_num":null,"u_region":null,"return_user_id":null,"wx_dk":"长城盛世家园一期","building_name":null,"response_timeout":null,"wx_house":null,"wx_way_id":"call_in","wx_content":"121","fclose_apply_reason":null,"wx_unit_id":null,"u_city_area_id":null,"return_visit_timeout":null,"handle_timeout":null,"wx_process_mode":null,"wx_build_id":null,"return_unsatisfy_do":null,"close_remark":null,"return_score":null,"return_threshold":null,"return_visit_result":null,"fclose_apply_time":null,"fclose_is_applying":0,"c_is_solve":0,"initData":{}}
         */

        private CustomerEnquiryModelBean customer_enquiry_model;

        public CustomerEnquiryModelBean getCustomer_enquiry_model() {
            return customer_enquiry_model;
        }

        public void setCustomer_enquiry_model(CustomerEnquiryModelBean customer_enquiry_model) {
            this.customer_enquiry_model = customer_enquiry_model;
        }

        public static class CustomerEnquiryModelBean {
            /**
             * handle_time : 2019-12-18 10:41:02
             * dispatch_close : null
             * fclose_applyer_id : null
             * handle_user : 越
             * fclose_approve_result : null
             * grid_code : null
             * housekeeper_name : null
             * proc_inst_id_ : 66465331870171140
             * return_user : null
             * wx_dk_id : 63872495547056133
             * housekeeper_account : null
             * wx_recorder_id : 65260234913873925
             * handle_cont : 回复内容 *:提交
             * grid_id : null
             * return_time : null
             * return_result : null
             * line_name : 环境
             * service_quality_content : null
             * state : return_visit
             * service_quality_score : null
             * fclose_approve_time : null
             * work_order_timeout : null
             * wx_recorder : 胡文越
             * wx_recorder_seat_number : null
             * app_state : null
             * wx_house_id : null
             * id_ : 66465331870172164
             * u_project_id : 63872468703510533
             * u_project : 长城盛世家园分公司
             * close_time : null
             * grid_name : null
             * unit_name : null
             * return_way_id : null
             * wx_user_id : null
             * service_attitude_content : null
             * house_code : null
             * wx_code : ccssjyyq-HJ-WX-20191218100002
             * tenant_id : 55614223698362369
             * fclose_apply_attach : null
             * wx_attachment : null
             * fclose_applyer : null
             * wx_cate_id : environment_virescence
             * wx_direct_reply : null
             * handle_user_id : 65260234913873925
             * u_region_id : null
             * wx_way : 来电
             * line_key : environmental_classification
             * c_deadline_time : 2019-12-25 10:37:43
             * ref_id_ : 0
             * fclose_approve_id : null
             * wx_user : 121
             * c_return_visit_status : null
             * wx_mobile : 13111111111
             * wx_time : 2019-12-18 10:37:43
             * u_city_area : null
             * wx_cate : 环境绿化类
             * return_way : null
             * return_visit_num : null
             * u_region : null
             * return_user_id : null
             * wx_dk : 长城盛世家园一期
             * building_name : null
             * response_timeout : null
             * wx_house : null
             * wx_way_id : call_in
             * wx_content : 121
             * fclose_apply_reason : null
             * wx_unit_id : null
             * u_city_area_id : null
             * return_visit_timeout : null
             * handle_timeout : null
             * wx_process_mode : null
             * wx_build_id : null
             * return_unsatisfy_do : null
             * close_remark : null
             * return_score : null
             * return_threshold : null
             * return_visit_result : null
             * fclose_apply_time : null
             * fclose_is_applying : 0
             * c_is_solve : 0
             * initData : {}
             */

            private String handle_time;
            private Object dispatch_close;
            private Object fclose_applyer_id;
            private String handle_user;
            private Object fclose_approve_result;
            private Object grid_code;
            private Object housekeeper_name;
            private String proc_inst_id_;
            private Object return_user;
            private String wx_dk_id;
            private Object housekeeper_account;
            private String wx_recorder_id;
            private String handle_cont;
            private Object grid_id;
            private Object return_time;
            private Object return_result;
            private String line_name;
            private Object service_quality_content;
            private String state;
            private Object service_quality_score;
            private Object fclose_approve_time;
            private Object work_order_timeout;
            private String wx_recorder;
            private Object wx_recorder_seat_number;
            private Object app_state;
            private Object wx_house_id;
            private String id_;
            private String u_project_id;
            private String u_project;
            private Object close_time;
            private Object grid_name;
            private Object unit_name;
            private Object return_way_id;
            private Object wx_user_id;
            private Object service_attitude_content;
            private Object house_code;
            private String wx_code;
            private String tenant_id;
            private Object fclose_apply_attach;
            private String wx_attachment;
            private Object fclose_applyer;
            private String wx_cate_id;
            private Object wx_direct_reply;
            private String handle_user_id;
            private Object u_region_id;
            private String wx_way;
            private String line_key;
            private String c_deadline_time;
            private String ref_id_;
            private Object fclose_approve_id;
            private String wx_user;
            private Object c_return_visit_status;
            private String wx_mobile;
            private String wx_time;
            private Object u_city_area;
            private String wx_cate;
            private Object return_way;
            private Object return_visit_num;
            private Object u_region;
            private Object return_user_id;
            private String wx_dk;
            private Object building_name;
            private Object response_timeout;
            private Object wx_house;
            private String wx_way_id;
            private String wx_content;
            private Object fclose_apply_reason;
            private Object wx_unit_id;
            private Object u_city_area_id;
            private Object return_visit_timeout;
            private Object handle_timeout;
            private Object wx_process_mode;
            private Object wx_build_id;
            private Object return_unsatisfy_do;
            private Object close_remark;
            private Object return_score;
            private Object return_threshold;
            private Object return_visit_result;
            private Object fclose_apply_time;
            private int fclose_is_applying;
            private int c_is_solve;
            private InitDataBean initData;

            public String getHandle_time() {
                return handle_time;
            }

            public void setHandle_time(String handle_time) {
                this.handle_time = handle_time;
            }

            public Object getDispatch_close() {
                return dispatch_close;
            }

            public void setDispatch_close(Object dispatch_close) {
                this.dispatch_close = dispatch_close;
            }

            public Object getFclose_applyer_id() {
                return fclose_applyer_id;
            }

            public void setFclose_applyer_id(Object fclose_applyer_id) {
                this.fclose_applyer_id = fclose_applyer_id;
            }

            public String getHandle_user() {
                return handle_user;
            }

            public void setHandle_user(String handle_user) {
                this.handle_user = handle_user;
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

            public String getProc_inst_id_() {
                return proc_inst_id_;
            }

            public void setProc_inst_id_(String proc_inst_id_) {
                this.proc_inst_id_ = proc_inst_id_;
            }

            public Object getReturn_user() {
                return return_user;
            }

            public void setReturn_user(Object return_user) {
                this.return_user = return_user;
            }

            public String getWx_dk_id() {
                return wx_dk_id;
            }

            public void setWx_dk_id(String wx_dk_id) {
                this.wx_dk_id = wx_dk_id;
            }

            public Object getHousekeeper_account() {
                return housekeeper_account;
            }

            public void setHousekeeper_account(Object housekeeper_account) {
                this.housekeeper_account = housekeeper_account;
            }

            public String getWx_recorder_id() {
                return wx_recorder_id;
            }

            public void setWx_recorder_id(String wx_recorder_id) {
                this.wx_recorder_id = wx_recorder_id;
            }

            public String getHandle_cont() {
                return handle_cont;
            }

            public void setHandle_cont(String handle_cont) {
                this.handle_cont = handle_cont;
            }

            public Object getGrid_id() {
                return grid_id;
            }

            public void setGrid_id(Object grid_id) {
                this.grid_id = grid_id;
            }

            public Object getReturn_time() {
                return return_time;
            }

            public void setReturn_time(Object return_time) {
                this.return_time = return_time;
            }

            public Object getReturn_result() {
                return return_result;
            }

            public void setReturn_result(Object return_result) {
                this.return_result = return_result;
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

            public Object getService_quality_score() {
                return service_quality_score;
            }

            public void setService_quality_score(Object service_quality_score) {
                this.service_quality_score = service_quality_score;
            }

            public Object getFclose_approve_time() {
                return fclose_approve_time;
            }

            public void setFclose_approve_time(Object fclose_approve_time) {
                this.fclose_approve_time = fclose_approve_time;
            }

            public Object getWork_order_timeout() {
                return work_order_timeout;
            }

            public void setWork_order_timeout(Object work_order_timeout) {
                this.work_order_timeout = work_order_timeout;
            }

            public String getWx_recorder() {
                return wx_recorder;
            }

            public void setWx_recorder(String wx_recorder) {
                this.wx_recorder = wx_recorder;
            }

            public Object getWx_recorder_seat_number() {
                return wx_recorder_seat_number;
            }

            public void setWx_recorder_seat_number(Object wx_recorder_seat_number) {
                this.wx_recorder_seat_number = wx_recorder_seat_number;
            }

            public Object getApp_state() {
                return app_state;
            }

            public void setApp_state(Object app_state) {
                this.app_state = app_state;
            }

            public Object getWx_house_id() {
                return wx_house_id;
            }

            public void setWx_house_id(Object wx_house_id) {
                this.wx_house_id = wx_house_id;
            }

            public String getId_() {
                return id_;
            }

            public void setId_(String id_) {
                this.id_ = id_;
            }

            public String getU_project_id() {
                return u_project_id;
            }

            public void setU_project_id(String u_project_id) {
                this.u_project_id = u_project_id;
            }

            public String getU_project() {
                return u_project;
            }

            public void setU_project(String u_project) {
                this.u_project = u_project;
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

            public Object getWx_user_id() {
                return wx_user_id;
            }

            public void setWx_user_id(Object wx_user_id) {
                this.wx_user_id = wx_user_id;
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

            public String getWx_code() {
                return wx_code;
            }

            public void setWx_code(String wx_code) {
                this.wx_code = wx_code;
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

            public String getWx_attachment() {
                return wx_attachment;
            }

            public void setWx_attachment(String wx_attachment) {
                this.wx_attachment = wx_attachment;
            }

            public Object getFclose_applyer() {
                return fclose_applyer;
            }

            public void setFclose_applyer(Object fclose_applyer) {
                this.fclose_applyer = fclose_applyer;
            }

            public String getWx_cate_id() {
                return wx_cate_id;
            }

            public void setWx_cate_id(String wx_cate_id) {
                this.wx_cate_id = wx_cate_id;
            }

            public Object getWx_direct_reply() {
                return wx_direct_reply;
            }

            public void setWx_direct_reply(Object wx_direct_reply) {
                this.wx_direct_reply = wx_direct_reply;
            }

            public String getHandle_user_id() {
                return handle_user_id;
            }

            public void setHandle_user_id(String handle_user_id) {
                this.handle_user_id = handle_user_id;
            }

            public Object getU_region_id() {
                return u_region_id;
            }

            public void setU_region_id(Object u_region_id) {
                this.u_region_id = u_region_id;
            }

            public String getWx_way() {
                return wx_way;
            }

            public void setWx_way(String wx_way) {
                this.wx_way = wx_way;
            }

            public String getLine_key() {
                return line_key;
            }

            public void setLine_key(String line_key) {
                this.line_key = line_key;
            }

            public String getC_deadline_time() {
                return c_deadline_time;
            }

            public void setC_deadline_time(String c_deadline_time) {
                this.c_deadline_time = c_deadline_time;
            }

            public String getRef_id_() {
                return ref_id_;
            }

            public void setRef_id_(String ref_id_) {
                this.ref_id_ = ref_id_;
            }

            public Object getFclose_approve_id() {
                return fclose_approve_id;
            }

            public void setFclose_approve_id(Object fclose_approve_id) {
                this.fclose_approve_id = fclose_approve_id;
            }

            public String getWx_user() {
                return wx_user;
            }

            public void setWx_user(String wx_user) {
                this.wx_user = wx_user;
            }

            public Object getC_return_visit_status() {
                return c_return_visit_status;
            }

            public void setC_return_visit_status(Object c_return_visit_status) {
                this.c_return_visit_status = c_return_visit_status;
            }

            public String getWx_mobile() {
                return wx_mobile;
            }

            public void setWx_mobile(String wx_mobile) {
                this.wx_mobile = wx_mobile;
            }

            public String getWx_time() {
                return wx_time;
            }

            public void setWx_time(String wx_time) {
                this.wx_time = wx_time;
            }

            public Object getU_city_area() {
                return u_city_area;
            }

            public void setU_city_area(Object u_city_area) {
                this.u_city_area = u_city_area;
            }

            public String getWx_cate() {
                return wx_cate;
            }

            public void setWx_cate(String wx_cate) {
                this.wx_cate = wx_cate;
            }

            public Object getReturn_way() {
                return return_way;
            }

            public void setReturn_way(Object return_way) {
                this.return_way = return_way;
            }

            public Object getReturn_visit_num() {
                return return_visit_num;
            }

            public void setReturn_visit_num(Object return_visit_num) {
                this.return_visit_num = return_visit_num;
            }

            public Object getU_region() {
                return u_region;
            }

            public void setU_region(Object u_region) {
                this.u_region = u_region;
            }

            public Object getReturn_user_id() {
                return return_user_id;
            }

            public void setReturn_user_id(Object return_user_id) {
                this.return_user_id = return_user_id;
            }

            public String getWx_dk() {
                return wx_dk;
            }

            public void setWx_dk(String wx_dk) {
                this.wx_dk = wx_dk;
            }

            public Object getBuilding_name() {
                return building_name;
            }

            public void setBuilding_name(Object building_name) {
                this.building_name = building_name;
            }

            public Object getResponse_timeout() {
                return response_timeout;
            }

            public void setResponse_timeout(Object response_timeout) {
                this.response_timeout = response_timeout;
            }

            public Object getWx_house() {
                return wx_house;
            }

            public void setWx_house(Object wx_house) {
                this.wx_house = wx_house;
            }

            public String getWx_way_id() {
                return wx_way_id;
            }

            public void setWx_way_id(String wx_way_id) {
                this.wx_way_id = wx_way_id;
            }

            public String getWx_content() {
                return wx_content;
            }

            public void setWx_content(String wx_content) {
                this.wx_content = wx_content;
            }

            public Object getFclose_apply_reason() {
                return fclose_apply_reason;
            }

            public void setFclose_apply_reason(Object fclose_apply_reason) {
                this.fclose_apply_reason = fclose_apply_reason;
            }

            public Object getWx_unit_id() {
                return wx_unit_id;
            }

            public void setWx_unit_id(Object wx_unit_id) {
                this.wx_unit_id = wx_unit_id;
            }

            public Object getU_city_area_id() {
                return u_city_area_id;
            }

            public void setU_city_area_id(Object u_city_area_id) {
                this.u_city_area_id = u_city_area_id;
            }

            public Object getReturn_visit_timeout() {
                return return_visit_timeout;
            }

            public void setReturn_visit_timeout(Object return_visit_timeout) {
                this.return_visit_timeout = return_visit_timeout;
            }

            public Object getHandle_timeout() {
                return handle_timeout;
            }

            public void setHandle_timeout(Object handle_timeout) {
                this.handle_timeout = handle_timeout;
            }

            public Object getWx_process_mode() {
                return wx_process_mode;
            }

            public void setWx_process_mode(Object wx_process_mode) {
                this.wx_process_mode = wx_process_mode;
            }

            public Object getWx_build_id() {
                return wx_build_id;
            }

            public void setWx_build_id(Object wx_build_id) {
                this.wx_build_id = wx_build_id;
            }

            public Object getReturn_unsatisfy_do() {
                return return_unsatisfy_do;
            }

            public void setReturn_unsatisfy_do(Object return_unsatisfy_do) {
                this.return_unsatisfy_do = return_unsatisfy_do;
            }

            public Object getClose_remark() {
                return close_remark;
            }

            public void setClose_remark(Object close_remark) {
                this.close_remark = close_remark;
            }

            public Object getReturn_score() {
                return return_score;
            }

            public void setReturn_score(Object return_score) {
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

            public int getC_is_solve() {
                return c_is_solve;
            }

            public void setC_is_solve(int c_is_solve) {
                this.c_is_solve = c_is_solve;
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

    public static class InfoBean {
        /**
         * formkey :
         * flowKey : customer_enquiry_flow
         * nodeId : ReturnVisit
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
