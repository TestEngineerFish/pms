package com.einyun.app.library.resource.workorder.model;

import com.google.gson.annotations.SerializedName;

public class OrderListModel {
    private String REF_ID;
    private String PROC_INST_ID;
    private String F_ORDER_NO;
    private Object F_PROJECT_NAME;
    private Object F_PROJECT_ID;
    private String F_DIVIDE_ID;
    private String F_DIVIDE_NAME;
    private String F_TX_ID;
    private String F_TX_CODE;
    private String F_TX_NAME;
    private String F_TYPE;
    private String F_TYPE_NAME;
    private String F_ENVIRMENT_TYPE2_CODE;
    private String F_ENVIRMENT_TYPE2_NAME;
    private String F_ENVIRMENT_TYPE3_CODE;
    private String F_ENVIRMENT_TYPE3_NAME;
    private String F_STATUS;
    private String F_DESC;
    private Object F_TIT_ID;
    private Object F_TIT_NAME;
    private String F_RES_ID;
    private String F_RES_NAME;
    private String F_DEADLINE_TIME;
    private String F_LOCATION;
    private String F_ACT_FINISH_TIME;
    private int F_OT_STATUS;
    private int F_EXT_STATUS;
    private String F_PROC_ID;
    private String F_PROC_NAME;
    private Object F_PROC_DATE;
    private Object F_PROC_CONTENT;
    private String F_CHECK_ID;
    private String F_CHECK_NAME;
    private Object F_CHECK_DATE;
    private Object F_CHECK_CONTENT;
    private Object F_CHECK_RESULT;
    private Object F_BEF_PIC;
    private Object F_AFT_PIC;
    private Object F_EVALUATION;
    private Object F_RETURN_REASON;
    private int F_HANG_STATUS;
    private int F_OT_LEVEL;
    private String F_CREATE_TIME;
    private int F_CLOSE;
    private String pgdAttachment;
    private Object F_ORIGINAL_ID;
    private Object F_ORIGINAL_TYPE;
    private Object F_ORIGINAL_CODE;
    private Object F_SCORE;
    private Object F_EXECUTOR_NAME;
    private String F_WP_ID;
    private String F_WP_NAME;
    private String F_WG_ID;
    private String F_WG_NAME;
    private String F_OWNER_ID;
    private String F_OWNER_NAME;
    private String F_PROCESS_ID;
    private String F_PROCESS_NAME;
    private String F_CONTENT;
    private String F_OPER_CONTENT;
    private String F_FILES;
    private String id_;
    private String RESPONSE_TIMEOUT;
    private String bx_dk_id;
    private String bx_way;
    private String bx_appoint_time_period;
    private String PROC_INST_TIMEOUT;
    private String bx_area;
    private String line_key;
    private String bx_dk;
    private String bx_user;
    private long c_deadline_time;
    private long bx_time;
    private String bx_mobile;
    private String bx_cate_lv1_id;
    private String assigneeName;
    private String bx_cate_lv3_id;
    private String line_name;
    private String state;
    private String bx_cate_lv2;
    private String bx_cate_lv1;
    private String bx_cate_lv3;
    private String app_state;
    private String ID_;
    private String Qualfieds;
    private String bx_area_id;
    private String bx_code;
    private String bx_content;
    private String u_project;
    private String QualfiedNames;
    private String assigneeId;
    private String bx_attachment;
    private String instance_id;
    private String bx_house_id;
    private String RETURN_VISIT_TIMEOUT;
    private String bx_cate_lv2_id;
    private long bx_appoint_time;
    private String taskId;
    private String F_ts_house;
    private String F_ts_cate_id;
    private String F_state;
    private String F_ts_mobile;
    private String F_line_name;
    private String F_ts_code;
    private String F_ts_property;
    private String F_ts_dk_id;
    private long F_ts_time;
    private String F_ts_user;
    private String F_ts_house_id;
    private String F_ts_attachment;
    private String F_ts_dk;
    private String F_app_state;
    private String F_ts_cate;
    private String F_ts_content;
    private String F_line_key;
    private String wx_dk;
    private String wx_attachment;
    private String wx_house_id;
    private String wx_house;
    private String wx_content;
    private String wx_cate_id;
    private String wx_way;
    private long close_time;
    private String wx_dk_id;
    private String wx_user;
    private String wx_mobile;
    private long wx_time;
    private String wx_cate;
    private String wx_code;
    private String F_project_id;
    private Object F_project_name;
    private String F_massif_id;
    private String F_massif_name;
    private String F_plan_work_order_code;
    private String F_line_id;
    private String F_type_id;
    private String F_type_name;
    private String F_inspection_work_guidance_id;
    private String F_inspection_work_guidance_name;
    private String F_inspection_work_plan_id;
    private String F_inspection_work_plan_name;
    private Object F_grid_id;
    private Object F_grid_name;
    private Object F_building_id;
    private Object F_building_name;
    private Object F_unit_id;
    private Object F_unit_name;
    private Object F_floor;
    private String F_completion_deadline;
    private Object F_actual_completion_time;
    private int F_is_time_out;
    private int F_plan_work_order_state;
    private String F_principal_id;
    private String F_principal_name;
    private String F_processing_person_id;
    private String F_processing_person_name;
    private Object F_processing_date;
    private String F_processing_instructions;
    private String F_processing_time;
    private long F_creation_date;
    private String F_files;
    private String F_description;
    private String F_line_code;
    private Object F_ext_status;
    private Object F_close;
    private Object F_house_code;
    private Object F_config_type;
    private Object F_patrol_line_id;
    private Object F_patrol_line_name;
    private Object F_duration;
    private Object F_real_duration;
    private Object is_sort;
    private String bx_house;

    public String getBx_house() {
        return bx_house;
    }

    public void setBx_house(String bx_house) {
        this.bx_house = bx_house;
    }

    public String getF_WP_ID() {
        return F_WP_ID;
    }

    public void setF_WP_ID(String f_WP_ID) {
        F_WP_ID = f_WP_ID;
    }

    public String getF_WP_NAME() {
        return F_WP_NAME;
    }

    public void setF_WP_NAME(String f_WP_NAME) {
        F_WP_NAME = f_WP_NAME;
    }

    public String getF_WG_ID() {
        return F_WG_ID;
    }

    public void setF_WG_ID(String f_WG_ID) {
        F_WG_ID = f_WG_ID;
    }

    public String getF_WG_NAME() {
        return F_WG_NAME;
    }

    public void setF_WG_NAME(String f_WG_NAME) {
        F_WG_NAME = f_WG_NAME;
    }

    public String getF_OWNER_ID() {
        return F_OWNER_ID;
    }

    public void setF_OWNER_ID(String f_OWNER_ID) {
        F_OWNER_ID = f_OWNER_ID;
    }

    public String getF_OWNER_NAME() {
        return F_OWNER_NAME;
    }

    public void setF_OWNER_NAME(String f_OWNER_NAME) {
        F_OWNER_NAME = f_OWNER_NAME;
    }

    public String getF_PROCESS_ID() {
        return F_PROCESS_ID;
    }

    public void setF_PROCESS_ID(String f_PROCESS_ID) {
        F_PROCESS_ID = f_PROCESS_ID;
    }

    public String getF_PROCESS_NAME() {
        return F_PROCESS_NAME;
    }

    public void setF_PROCESS_NAME(String f_PROCESS_NAME) {
        F_PROCESS_NAME = f_PROCESS_NAME;
    }

    public String getF_CONTENT() {
        return F_CONTENT;
    }

    public void setF_CONTENT(String f_CONTENT) {
        F_CONTENT = f_CONTENT;
    }

    public String getF_OPER_CONTENT() {
        return F_OPER_CONTENT;
    }

    public void setF_OPER_CONTENT(String f_OPER_CONTENT) {
        F_OPER_CONTENT = f_OPER_CONTENT;
    }

    public String getF_FILES() {
        return F_FILES;
    }

    public void setF_FILES(String f_FILES) {
        F_FILES = f_FILES;
    }

    public String getId() {
        return id_;
    }

    public void setId(String id) {
        this.id_ = id;
    }

    public String getREF_ID() {
        return REF_ID;
    }

    public void setREF_ID(String REF_ID) {
        this.REF_ID = REF_ID;
    }

    public String getPROC_INST_ID() {
        return PROC_INST_ID;
    }

    public void setPROC_INST_ID(String PROC_INST_ID) {
        this.PROC_INST_ID = PROC_INST_ID;
    }

    public String getF_ORDER_NO() {
        return F_ORDER_NO;
    }

    public void setF_ORDER_NO(String F_ORDER_NO) {
        this.F_ORDER_NO = F_ORDER_NO;
    }

    public Object getF_PROJECT_NAME() {
        return F_PROJECT_NAME;
    }

    public void setF_PROJECT_NAME(Object F_PROJECT_NAME) {
        this.F_PROJECT_NAME = F_PROJECT_NAME;
    }

    public Object getF_PROJECT_ID() {
        return F_PROJECT_ID;
    }

    public void setF_PROJECT_ID(Object F_PROJECT_ID) {
        this.F_PROJECT_ID = F_PROJECT_ID;
    }

    public String getF_DIVIDE_ID() {
        return F_DIVIDE_ID;
    }

    public void setF_DIVIDE_ID(String F_DIVIDE_ID) {
        this.F_DIVIDE_ID = F_DIVIDE_ID;
    }

    public String getF_DIVIDE_NAME() {
        return F_DIVIDE_NAME;
    }

    public void setF_DIVIDE_NAME(String F_DIVIDE_NAME) {
        this.F_DIVIDE_NAME = F_DIVIDE_NAME;
    }

    public String getF_TX_ID() {
        return F_TX_ID;
    }

    public void setF_TX_ID(String F_TX_ID) {
        this.F_TX_ID = F_TX_ID;
    }

    public String getF_TX_CODE() {
        return F_TX_CODE;
    }

    public void setF_TX_CODE(String F_TX_CODE) {
        this.F_TX_CODE = F_TX_CODE;
    }

    public String getF_TX_NAME() {
        return F_TX_NAME;
    }

    public void setF_TX_NAME(String F_TX_NAME) {
        this.F_TX_NAME = F_TX_NAME;
    }

    public String getF_TYPE() {
        return F_TYPE;
    }

    public void setF_TYPE(String F_TYPE) {
        this.F_TYPE = F_TYPE;
    }

    public String getF_TYPE_NAME() {
        return F_TYPE_NAME;
    }

    public void setF_TYPE_NAME(String F_TYPE_NAME) {
        this.F_TYPE_NAME = F_TYPE_NAME;
    }

    public String getF_ENVIRMENT_TYPE2_CODE() {
        return F_ENVIRMENT_TYPE2_CODE;
    }

    public void setF_ENVIRMENT_TYPE2_CODE(String F_ENVIRMENT_TYPE2_CODE) {
        this.F_ENVIRMENT_TYPE2_CODE = F_ENVIRMENT_TYPE2_CODE;
    }

    public String getF_ENVIRMENT_TYPE2_NAME() {
        return F_ENVIRMENT_TYPE2_NAME;
    }

    public void setF_ENVIRMENT_TYPE2_NAME(String F_ENVIRMENT_TYPE2_NAME) {
        this.F_ENVIRMENT_TYPE2_NAME = F_ENVIRMENT_TYPE2_NAME;
    }

    public String getF_ENVIRMENT_TYPE3_CODE() {
        return F_ENVIRMENT_TYPE3_CODE;
    }

    public void setF_ENVIRMENT_TYPE3_CODE(String F_ENVIRMENT_TYPE3_CODE) {
        this.F_ENVIRMENT_TYPE3_CODE = F_ENVIRMENT_TYPE3_CODE;
    }

    public String getF_ENVIRMENT_TYPE3_NAME() {
        return F_ENVIRMENT_TYPE3_NAME;
    }

    public void setF_ENVIRMENT_TYPE3_NAME(String F_ENVIRMENT_TYPE3_NAME) {
        this.F_ENVIRMENT_TYPE3_NAME = F_ENVIRMENT_TYPE3_NAME;
    }

    public String getF_STATUS() {
        return F_STATUS;
    }

    public void setF_STATUS(String F_STATUS) {
        this.F_STATUS = F_STATUS;
    }

    public String getF_DESC() {
        return F_DESC;
    }

    public void setF_DESC(String F_DESC) {
        this.F_DESC = F_DESC;
    }

    public Object getF_TIT_ID() {
        return F_TIT_ID;
    }

    public void setF_TIT_ID(Object F_TIT_ID) {
        this.F_TIT_ID = F_TIT_ID;
    }

    public Object getF_TIT_NAME() {
        return F_TIT_NAME;
    }

    public void setF_TIT_NAME(Object F_TIT_NAME) {
        this.F_TIT_NAME = F_TIT_NAME;
    }

    public String getF_RES_ID() {
        return F_RES_ID;
    }

    public void setF_RES_ID(String F_RES_ID) {
        this.F_RES_ID = F_RES_ID;
    }

    public String getF_RES_NAME() {
        return F_RES_NAME;
    }

    public void setF_RES_NAME(String F_RES_NAME) {
        this.F_RES_NAME = F_RES_NAME;
    }

    public String getF_DEADLINE_TIME() {
        return F_DEADLINE_TIME;
    }

    public void setF_DEADLINE_TIME(String F_DEADLINE_TIME) {
        this.F_DEADLINE_TIME = F_DEADLINE_TIME;
    }

    public String getF_LOCATION() {
        return F_LOCATION;
    }

    public void setF_LOCATION(String F_LOCATION) {
        this.F_LOCATION = F_LOCATION;
    }

    public String getF_ACT_FINISH_TIME() {
        return F_ACT_FINISH_TIME;
    }

    public void setF_ACT_FINISH_TIME(String F_ACT_FINISH_TIME) {
        this.F_ACT_FINISH_TIME = F_ACT_FINISH_TIME;
    }

    public int getF_OT_STATUS() {
        return F_OT_STATUS;
    }

    public void setF_OT_STATUS(int F_OT_STATUS) {
        this.F_OT_STATUS = F_OT_STATUS;
    }

    public int getF_EXT_STATUS() {
        return F_EXT_STATUS;
    }

    public void setF_EXT_STATUS(int F_EXT_STATUS) {
        this.F_EXT_STATUS = F_EXT_STATUS;
    }

    public String getF_PROC_ID() {
        return F_PROC_ID;
    }

    public void setF_PROC_ID(String F_PROC_ID) {
        this.F_PROC_ID = F_PROC_ID;
    }

    public String getF_PROC_NAME() {
        return F_PROC_NAME;
    }

    public void setF_PROC_NAME(String F_PROC_NAME) {
        this.F_PROC_NAME = F_PROC_NAME;
    }

    public Object getF_PROC_DATE() {
        return F_PROC_DATE;
    }

    public void setF_PROC_DATE(Object F_PROC_DATE) {
        this.F_PROC_DATE = F_PROC_DATE;
    }

    public Object getF_PROC_CONTENT() {
        return F_PROC_CONTENT;
    }

    public void setF_PROC_CONTENT(Object F_PROC_CONTENT) {
        this.F_PROC_CONTENT = F_PROC_CONTENT;
    }

    public String getF_CHECK_ID() {
        return F_CHECK_ID;
    }

    public void setF_CHECK_ID(String F_CHECK_ID) {
        this.F_CHECK_ID = F_CHECK_ID;
    }

    public String getF_CHECK_NAME() {
        return F_CHECK_NAME;
    }

    public void setF_CHECK_NAME(String F_CHECK_NAME) {
        this.F_CHECK_NAME = F_CHECK_NAME;
    }

    public Object getF_CHECK_DATE() {
        return F_CHECK_DATE;
    }

    public void setF_CHECK_DATE(Object F_CHECK_DATE) {
        this.F_CHECK_DATE = F_CHECK_DATE;
    }

    public Object getF_CHECK_CONTENT() {
        return F_CHECK_CONTENT;
    }

    public void setF_CHECK_CONTENT(Object F_CHECK_CONTENT) {
        this.F_CHECK_CONTENT = F_CHECK_CONTENT;
    }

    public Object getF_CHECK_RESULT() {
        return F_CHECK_RESULT;
    }

    public void setF_CHECK_RESULT(Object F_CHECK_RESULT) {
        this.F_CHECK_RESULT = F_CHECK_RESULT;
    }

    public Object getF_BEF_PIC() {
        return F_BEF_PIC;
    }

    public void setF_BEF_PIC(Object F_BEF_PIC) {
        this.F_BEF_PIC = F_BEF_PIC;
    }

    public Object getF_AFT_PIC() {
        return F_AFT_PIC;
    }

    public void setF_AFT_PIC(Object F_AFT_PIC) {
        this.F_AFT_PIC = F_AFT_PIC;
    }

    public Object getF_EVALUATION() {
        return F_EVALUATION;
    }

    public void setF_EVALUATION(Object F_EVALUATION) {
        this.F_EVALUATION = F_EVALUATION;
    }

    public Object getF_RETURN_REASON() {
        return F_RETURN_REASON;
    }

    public void setF_RETURN_REASON(Object F_RETURN_REASON) {
        this.F_RETURN_REASON = F_RETURN_REASON;
    }

    public int getF_HANG_STATUS() {
        return F_HANG_STATUS;
    }

    public void setF_HANG_STATUS(int F_HANG_STATUS) {
        this.F_HANG_STATUS = F_HANG_STATUS;
    }

    public int getF_OT_LEVEL() {
        return F_OT_LEVEL;
    }

    public void setF_OT_LEVEL(int F_OT_LEVEL) {
        this.F_OT_LEVEL = F_OT_LEVEL;
    }

    public String getF_CREATE_TIME() {
        return F_CREATE_TIME;
    }

    public void setF_CREATE_TIME(String F_CREATE_TIME) {
        this.F_CREATE_TIME = F_CREATE_TIME;
    }

    public int getF_CLOSE() {
        return F_CLOSE;
    }

    public void setF_CLOSE(int F_CLOSE) {
        this.F_CLOSE = F_CLOSE;
    }

    public String getPgdAttachment() {
        return pgdAttachment;
    }

    public void setPgdAttachment(String pgdAttachment) {
        this.pgdAttachment = pgdAttachment;
    }

    public Object getF_ORIGINAL_ID() {
        return F_ORIGINAL_ID;
    }

    public void setF_ORIGINAL_ID(Object F_ORIGINAL_ID) {
        this.F_ORIGINAL_ID = F_ORIGINAL_ID;
    }

    public Object getF_ORIGINAL_TYPE() {
        return F_ORIGINAL_TYPE;
    }

    public void setF_ORIGINAL_TYPE(Object F_ORIGINAL_TYPE) {
        this.F_ORIGINAL_TYPE = F_ORIGINAL_TYPE;
    }

    public Object getF_ORIGINAL_CODE() {
        return F_ORIGINAL_CODE;
    }

    public void setF_ORIGINAL_CODE(Object F_ORIGINAL_CODE) {
        this.F_ORIGINAL_CODE = F_ORIGINAL_CODE;
    }

    public Object getF_SCORE() {
        return F_SCORE;
    }

    public void setF_SCORE(Object F_SCORE) {
        this.F_SCORE = F_SCORE;
    }

    public Object getF_EXECUTOR_NAME() {
        return F_EXECUTOR_NAME;
    }

    public void setF_EXECUTOR_NAME(Object F_EXECUTOR_NAME) {
        this.F_EXECUTOR_NAME = F_EXECUTOR_NAME;
    }

    public String getRESPONSE_TIMEOUT() {
        return RESPONSE_TIMEOUT;
    }

    public void setRESPONSE_TIMEOUT(String RESPONSE_TIMEOUT) {
        this.RESPONSE_TIMEOUT = RESPONSE_TIMEOUT;
    }

    public String getBx_dk_id() {
        return bx_dk_id;
    }

    public void setBx_dk_id(String bx_dk_id) {
        this.bx_dk_id = bx_dk_id;
    }

    public String getBx_way() {
        return bx_way;
    }

    public void setBx_way(String bx_way) {
        this.bx_way = bx_way;
    }

    public String getBx_appoint_time_period() {
        return bx_appoint_time_period;
    }

    public void setBx_appoint_time_period(String bx_appoint_time_period) {
        this.bx_appoint_time_period = bx_appoint_time_period;
    }

    public String getPROC_INST_TIMEOUT() {
        return PROC_INST_TIMEOUT;
    }

    public void setPROC_INST_TIMEOUT(String PROC_INST_TIMEOUT) {
        this.PROC_INST_TIMEOUT = PROC_INST_TIMEOUT;
    }

    public String getBx_area() {
        return bx_area;
    }

    public void setBx_area(String bx_area) {
        this.bx_area = bx_area;
    }

    public String getLine_key() {
        return line_key;
    }

    public void setLine_key(String line_key) {
        this.line_key = line_key;
    }

    public String getBx_dk() {
        return bx_dk;
    }

    public void setBx_dk(String bx_dk) {
        this.bx_dk = bx_dk;
    }

    public String getBx_user() {
        return bx_user;
    }

    public void setBx_user(String bx_user) {
        this.bx_user = bx_user;
    }

    public long getC_deadline_time() {
        return c_deadline_time;
    }

    public void setC_deadline_time(long c_deadline_time) {
        this.c_deadline_time = c_deadline_time;
    }

    public long getBx_time() {
        return bx_time;
    }

    public void setBx_time(long bx_time) {
        this.bx_time = bx_time;
    }

    public String getBx_mobile() {
        return bx_mobile;
    }

    public void setBx_mobile(String bx_mobile) {
        this.bx_mobile = bx_mobile;
    }

    public String getBx_cate_lv1_id() {
        return bx_cate_lv1_id;
    }

    public void setBx_cate_lv1_id(String bx_cate_lv1_id) {
        this.bx_cate_lv1_id = bx_cate_lv1_id;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getBx_cate_lv3_id() {
        return bx_cate_lv3_id;
    }

    public void setBx_cate_lv3_id(String bx_cate_lv3_id) {
        this.bx_cate_lv3_id = bx_cate_lv3_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBx_cate_lv2() {
        return bx_cate_lv2;
    }

    public void setBx_cate_lv2(String bx_cate_lv2) {
        this.bx_cate_lv2 = bx_cate_lv2;
    }

    public String getBx_cate_lv1() {
        return bx_cate_lv1;
    }

    public void setBx_cate_lv1(String bx_cate_lv1) {
        this.bx_cate_lv1 = bx_cate_lv1;
    }

    public String getBx_cate_lv3() {
        return bx_cate_lv3;
    }

    public void setBx_cate_lv3(String bx_cate_lv3) {
        this.bx_cate_lv3 = bx_cate_lv3;
    }

    public String getApp_state() {
        return app_state;
    }

    public void setApp_state(String app_state) {
        this.app_state = app_state;
    }

    public String getID_() {
        return ID_;
    }

    public void setID_(String ID_) {
        this.ID_ = ID_;
    }

    public String getQualfieds() {
        return Qualfieds;
    }

    public void setQualfieds(String Qualfieds) {
        this.Qualfieds = Qualfieds;
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

    public String getBx_content() {
        return bx_content;
    }

    public void setBx_content(String bx_content) {
        this.bx_content = bx_content;
    }

    public String getU_project() {
        return u_project;
    }

    public void setU_project(String u_project) {
        this.u_project = u_project;
    }

    public String getQualfiedNames() {
        return QualfiedNames;
    }

    public void setQualfiedNames(String QualfiedNames) {
        this.QualfiedNames = QualfiedNames;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getBx_attachment() {
        return bx_attachment;
    }

    public void setBx_attachment(String bx_attachment) {
        this.bx_attachment = bx_attachment;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getBx_house_id() {
        return bx_house_id;
    }

    public void setBx_house_id(String bx_house_id) {
        this.bx_house_id = bx_house_id;
    }

    public String getRETURN_VISIT_TIMEOUT() {
        return RETURN_VISIT_TIMEOUT;
    }

    public void setRETURN_VISIT_TIMEOUT(String RETURN_VISIT_TIMEOUT) {
        this.RETURN_VISIT_TIMEOUT = RETURN_VISIT_TIMEOUT;
    }

    public String getBx_cate_lv2_id() {
        return bx_cate_lv2_id;
    }

    public void setBx_cate_lv2_id(String bx_cate_lv2_id) {
        this.bx_cate_lv2_id = bx_cate_lv2_id;
    }

    public long getBx_appoint_time() {
        return bx_appoint_time;
    }

    public void setBx_appoint_time(long bx_appoint_time) {
        this.bx_appoint_time = bx_appoint_time;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getF_ts_house() {
        return F_ts_house;
    }

    public void setF_ts_house(String F_ts_house) {
        this.F_ts_house = F_ts_house;
    }

    public String getF_ts_cate_id() {
        return F_ts_cate_id;
    }

    public void setF_ts_cate_id(String F_ts_cate_id) {
        this.F_ts_cate_id = F_ts_cate_id;
    }

    public String getF_state() {
        return F_state;
    }

    public void setF_state(String F_state) {
        this.F_state = F_state;
    }

    public String getF_ts_mobile() {
        return F_ts_mobile;
    }

    public void setF_ts_mobile(String F_ts_mobile) {
        this.F_ts_mobile = F_ts_mobile;
    }

    public String getF_line_name() {
        return F_line_name;
    }

    public void setF_line_name(String F_line_name) {
        this.F_line_name = F_line_name;
    }

    public String getF_ts_code() {
        return F_ts_code;
    }

    public void setF_ts_code(String F_ts_code) {
        this.F_ts_code = F_ts_code;
    }

    public String getF_ts_property() {
        return F_ts_property;
    }

    public void setF_ts_property(String F_ts_property) {
        this.F_ts_property = F_ts_property;
    }

    public String getF_ts_dk_id() {
        return F_ts_dk_id;
    }

    public void setF_ts_dk_id(String F_ts_dk_id) {
        this.F_ts_dk_id = F_ts_dk_id;
    }

    public long getF_ts_time() {
        return F_ts_time;
    }

    public void setF_ts_time(long F_ts_time) {
        this.F_ts_time = F_ts_time;
    }

    public String getF_ts_user() {
        return F_ts_user;
    }

    public void setF_ts_user(String F_ts_user) {
        this.F_ts_user = F_ts_user;
    }

    public String getF_ts_house_id() {
        return F_ts_house_id;
    }

    public void setF_ts_house_id(String F_ts_house_id) {
        this.F_ts_house_id = F_ts_house_id;
    }

    public String getF_ts_attachment() {
        return F_ts_attachment;
    }

    public void setF_ts_attachment(String F_ts_attachment) {
        this.F_ts_attachment = F_ts_attachment;
    }

    public String getF_ts_dk() {
        return F_ts_dk;
    }

    public void setF_ts_dk(String F_ts_dk) {
        this.F_ts_dk = F_ts_dk;
    }

    public String getF_app_state() {
        return F_app_state;
    }

    public void setF_app_state(String F_app_state) {
        this.F_app_state = F_app_state;
    }

    public String getF_ts_cate() {
        return F_ts_cate;
    }

    public void setF_ts_cate(String F_ts_cate) {
        this.F_ts_cate = F_ts_cate;
    }

    public String getF_ts_content() {
        return F_ts_content;
    }

    public void setF_ts_content(String F_ts_content) {
        this.F_ts_content = F_ts_content;
    }

    public String getF_line_key() {
        return F_line_key;
    }

    public void setF_line_key(String F_line_key) {
        this.F_line_key = F_line_key;
    }

    public String getWx_dk() {
        return wx_dk;
    }

    public void setWx_dk(String wx_dk) {
        this.wx_dk = wx_dk;
    }

    public String getWx_attachment() {
        return wx_attachment;
    }

    public void setWx_attachment(String wx_attachment) {
        this.wx_attachment = wx_attachment;
    }

    public String getWx_house_id() {
        return wx_house_id;
    }

    public void setWx_house_id(String wx_house_id) {
        this.wx_house_id = wx_house_id;
    }

    public String getWx_house() {
        return wx_house;
    }

    public void setWx_house(String wx_house) {
        this.wx_house = wx_house;
    }

    public String getWx_content() {
        return wx_content;
    }

    public void setWx_content(String wx_content) {
        this.wx_content = wx_content;
    }

    public String getWx_cate_id() {
        return wx_cate_id;
    }

    public void setWx_cate_id(String wx_cate_id) {
        this.wx_cate_id = wx_cate_id;
    }

    public String getWx_way() {
        return wx_way;
    }

    public void setWx_way(String wx_way) {
        this.wx_way = wx_way;
    }

    public long getClose_time() {
        return close_time;
    }

    public void setClose_time(long close_time) {
        this.close_time = close_time;
    }

    public String getWx_dk_id() {
        return wx_dk_id;
    }

    public void setWx_dk_id(String wx_dk_id) {
        this.wx_dk_id = wx_dk_id;
    }

    public String getWx_user() {
        return wx_user;
    }

    public void setWx_user(String wx_user) {
        this.wx_user = wx_user;
    }

    public String getWx_mobile() {
        return wx_mobile;
    }

    public void setWx_mobile(String wx_mobile) {
        this.wx_mobile = wx_mobile;
    }

    public long getWx_time() {
        return wx_time;
    }

    public void setWx_time(long wx_time) {
        this.wx_time = wx_time;
    }

    public String getWx_cate() {
        return wx_cate;
    }

    public void setWx_cate(String wx_cate) {
        this.wx_cate = wx_cate;
    }

    public String getWx_code() {
        return wx_code;
    }

    public void setWx_code(String wx_code) {
        this.wx_code = wx_code;
    }

    public String getF_project_id() {
        return F_project_id;
    }

    public void setF_project_id(String F_project_id) {
        this.F_project_id = F_project_id;
    }

    public Object getF_project_name() {
        return F_project_name;
    }

    public void setF_project_name(Object F_project_name) {
        this.F_project_name = F_project_name;
    }

    public String getF_massif_id() {
        return F_massif_id;
    }

    public void setF_massif_id(String F_massif_id) {
        this.F_massif_id = F_massif_id;
    }

    public String getF_massif_name() {
        return F_massif_name;
    }

    public void setF_massif_name(String F_massif_name) {
        this.F_massif_name = F_massif_name;
    }

    public String getF_plan_work_order_code() {
        return F_plan_work_order_code;
    }

    public void setF_plan_work_order_code(String F_plan_work_order_code) {
        this.F_plan_work_order_code = F_plan_work_order_code;
    }

    public String getF_line_id() {
        return F_line_id;
    }

    public void setF_line_id(String F_line_id) {
        this.F_line_id = F_line_id;
    }

    public String getF_type_id() {
        return F_type_id;
    }

    public void setF_type_id(String F_type_id) {
        this.F_type_id = F_type_id;
    }

    public String getF_type_name() {
        return F_type_name;
    }

    public void setF_type_name(String F_type_name) {
        this.F_type_name = F_type_name;
    }

    public String getF_inspection_work_guidance_id() {
        return F_inspection_work_guidance_id;
    }

    public void setF_inspection_work_guidance_id(String F_inspection_work_guidance_id) {
        this.F_inspection_work_guidance_id = F_inspection_work_guidance_id;
    }

    public String getF_inspection_work_guidance_name() {
        return F_inspection_work_guidance_name;
    }

    public void setF_inspection_work_guidance_name(String F_inspection_work_guidance_name) {
        this.F_inspection_work_guidance_name = F_inspection_work_guidance_name;
    }

    public String getF_inspection_work_plan_id() {
        return F_inspection_work_plan_id;
    }

    public void setF_inspection_work_plan_id(String F_inspection_work_plan_id) {
        this.F_inspection_work_plan_id = F_inspection_work_plan_id;
    }

    public String getF_inspection_work_plan_name() {
        return F_inspection_work_plan_name;
    }

    public void setF_inspection_work_plan_name(String F_inspection_work_plan_name) {
        this.F_inspection_work_plan_name = F_inspection_work_plan_name;
    }

    public Object getF_grid_id() {
        return F_grid_id;
    }

    public void setF_grid_id(Object F_grid_id) {
        this.F_grid_id = F_grid_id;
    }

    public Object getF_grid_name() {
        return F_grid_name;
    }

    public void setF_grid_name(Object F_grid_name) {
        this.F_grid_name = F_grid_name;
    }

    public Object getF_building_id() {
        return F_building_id;
    }

    public void setF_building_id(Object F_building_id) {
        this.F_building_id = F_building_id;
    }

    public Object getF_building_name() {
        return F_building_name;
    }

    public void setF_building_name(Object F_building_name) {
        this.F_building_name = F_building_name;
    }

    public Object getF_unit_id() {
        return F_unit_id;
    }

    public void setF_unit_id(Object F_unit_id) {
        this.F_unit_id = F_unit_id;
    }

    public Object getF_unit_name() {
        return F_unit_name;
    }

    public void setF_unit_name(Object F_unit_name) {
        this.F_unit_name = F_unit_name;
    }

    public Object getF_floor() {
        return F_floor;
    }

    public void setF_floor(Object F_floor) {
        this.F_floor = F_floor;
    }

    public String getF_completion_deadline() {
        return F_completion_deadline;
    }

    public void setF_completion_deadline(String F_completion_deadline) {
        this.F_completion_deadline = F_completion_deadline;
    }

    public Object getF_actual_completion_time() {
        return F_actual_completion_time;
    }

    public void setF_actual_completion_time(Object F_actual_completion_time) {
        this.F_actual_completion_time = F_actual_completion_time;
    }

    public int getF_is_time_out() {
        return F_is_time_out;
    }

    public void setF_is_time_out(int F_is_time_out) {
        this.F_is_time_out = F_is_time_out;
    }

    public int getF_plan_work_order_state() {
        return F_plan_work_order_state;
    }

    public void setF_plan_work_order_state(int F_plan_work_order_state) {
        this.F_plan_work_order_state = F_plan_work_order_state;
    }

    public String getF_principal_id() {
        return F_principal_id;
    }

    public void setF_principal_id(String F_principal_id) {
        this.F_principal_id = F_principal_id;
    }

    public String getF_principal_name() {
        return F_principal_name;
    }

    public void setF_principal_name(String F_principal_name) {
        this.F_principal_name = F_principal_name;
    }

    public String getF_processing_person_id() {
        return F_processing_person_id;
    }

    public void setF_processing_person_id(String F_processing_person_id) {
        this.F_processing_person_id = F_processing_person_id;
    }

    public String getF_processing_person_name() {
        return F_processing_person_name;
    }

    public void setF_processing_person_name(String F_processing_person_name) {
        this.F_processing_person_name = F_processing_person_name;
    }

    public Object getF_processing_date() {
        return F_processing_date;
    }

    public void setF_processing_date(Object F_processing_date) {
        this.F_processing_date = F_processing_date;
    }

    public String getF_processing_instructions() {
        return F_processing_instructions;
    }

    public void setF_processing_instructions(String F_processing_instructions) {
        this.F_processing_instructions = F_processing_instructions;
    }

    public String getF_processing_time() {
        return F_processing_time;
    }

    public void setF_processing_time(String F_processing_time) {
        this.F_processing_time = F_processing_time;
    }

    public long getF_creation_date() {
        return F_creation_date;
    }

    public void setF_creation_date(long F_creation_date) {
        this.F_creation_date = F_creation_date;
    }

    public String getF_files() {
        return F_files;
    }

    public void setF_files(String F_files) {
        this.F_files = F_files;
    }

    public String getF_description() {
        return F_description;
    }

    public void setF_description(String F_description) {
        this.F_description = F_description;
    }

    public String getF_line_code() {
        return F_line_code;
    }

    public void setF_line_code(String F_line_code) {
        this.F_line_code = F_line_code;
    }

    public Object getF_ext_status() {
        return F_ext_status;
    }

    public void setF_ext_status(Object F_ext_status) {
        this.F_ext_status = F_ext_status;
    }

    public Object getF_close() {
        return F_close;
    }

    public void setF_close(Object F_close) {
        this.F_close = F_close;
    }

    public Object getF_house_code() {
        return F_house_code;
    }

    public void setF_house_code(Object F_house_code) {
        this.F_house_code = F_house_code;
    }

    public Object getF_config_type() {
        return F_config_type;
    }

    public void setF_config_type(Object F_config_type) {
        this.F_config_type = F_config_type;
    }

    public Object getF_patrol_line_id() {
        return F_patrol_line_id;
    }

    public void setF_patrol_line_id(Object F_patrol_line_id) {
        this.F_patrol_line_id = F_patrol_line_id;
    }

    public Object getF_patrol_line_name() {
        return F_patrol_line_name;
    }

    public void setF_patrol_line_name(Object F_patrol_line_name) {
        this.F_patrol_line_name = F_patrol_line_name;
    }

    public Object getF_duration() {
        return F_duration;
    }

    public void setF_duration(Object F_duration) {
        this.F_duration = F_duration;
    }

    public Object getF_real_duration() {
        return F_real_duration;
    }

    public void setF_real_duration(Object F_real_duration) {
        this.F_real_duration = F_real_duration;
    }

    public Object getIs_sort() {
        return is_sort;
    }

    public void setIs_sort(Object is_sort) {
        this.is_sort = is_sort;
    }
}
