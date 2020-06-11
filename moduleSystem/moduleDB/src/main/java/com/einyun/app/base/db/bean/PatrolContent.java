package com.einyun.app.base.db.bean;

import androidx.annotation.NonNull;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.converter.InitDataTypeConvert;

import java.util.List;

public class PatrolContent{
    private String F_massif_name;
    private String F_line_name;
    private String F_building_id;
    private String F_processing_instructions;
    private String F_project_name;
    private String F_type_name;
    private String F_unit_id;
    private String F_processing_time;
    private String proc_inst_id_;
    private String F_unit_name;
    private String F_tit_name;
    private String F_principal_name;
    private String F_processing_person_id;
    private String F_processing_person_name;
    private String F_building_name;
    private String REF_ID_;
    private String F_project_id;
    private String F_line_id;
    private String F_inspection_work_plan_name;
    private String F_grid_id;
    private String F_hang_status;
    private String F_inspection_work_guidance_name;
    private String F_files;
    private int F_plan_work_order_state;
    private String F_principal_id;
    private String id_;
    private String F_processing_date;
    private String F_tit_id;
    private String F_SEND_REMARK ;
    private String F_creation_date;
    private String F_type_id;
    private String F_line_code;
    private String F_massif_id;
    private String F_inspection_work_plan_id;
    private String F_grid_name;
    private String F_house_code;
    private int F_floor;
    private String F_inspection_work_guidance_id;
    private String F_description;
    private int F_ot_hours;
    private String F_completion_deadline;
    private String F_actual_completion_time;
    private String F_plan_work_order_code;
    private int F_is_time_out;
    @TypeConverters(InitDataTypeConvert.class)
    private InitData initData;
    private List<SubInspectionWorkOrderFlowNode> sub_inspection_work_order_flow_node;
    private int is_sort;
    private String F_patrol_line_name;
    private String F_patrol_line_id;
    private int  F_duration;

    public int getIs_sort() {
        return is_sort;
    }

    public void setIs_sort(int is_sort) {
        this.is_sort = is_sort;
    }

    public String getF_patrol_line_name() {
        return F_patrol_line_name;
    }

    public void setF_patrol_line_name(String f_patrol_line_name) {
        F_patrol_line_name = f_patrol_line_name;
    }

    public int getF_duration() {
        return F_duration;
    }

    public void setF_duration(int f_duration) {
        F_duration = f_duration;
    }

    public String getF_massif_name() {
        return F_massif_name;
    }

    public void setF_massif_name(String f_massif_name) {
        F_massif_name = f_massif_name;
    }

    public String getF_line_name() {
        return F_line_name;
    }

    public void setF_line_name(String f_line_name) {
        F_line_name = f_line_name;
    }

    public String getF_building_id() {
        return F_building_id;
    }

    public void setF_building_id(String f_building_id) {
        F_building_id = f_building_id;
    }

    public String getF_processing_instructions() {
        return F_processing_instructions;
    }

    public void setF_processing_instructions(String f_processing_instructions) {
        F_processing_instructions = f_processing_instructions;
    }

    public String getF_project_name() {
        return F_project_name;
    }

    public void setF_project_name(String f_project_name) {
        F_project_name = f_project_name;
    }

    public String getF_type_name() {
        return F_type_name;
    }

    public void setF_type_name(String f_type_name) {
        F_type_name = f_type_name;
    }

    public String getF_unit_id() {
        return F_unit_id;
    }

    public void setF_unit_id(String f_unit_id) {
        F_unit_id = f_unit_id;
    }

    public String getF_processing_time() {
        return F_processing_time;
    }

    public void setF_processing_time(String f_processing_time) {
        F_processing_time = f_processing_time;
    }

    public String getProc_inst_id_() {
        return proc_inst_id_;
    }

    public void setProc_inst_id_(String proc_inst_id_) {
        this.proc_inst_id_ = proc_inst_id_;
    }

    public String getF_unit_name() {
        return F_unit_name;
    }

    public void setF_unit_name(String f_unit_name) {
        F_unit_name = f_unit_name;
    }

    public String getF_tit_name() {
        return F_tit_name;
    }

    public void setF_tit_name(String f_tit_name) {
        F_tit_name = f_tit_name;
    }

    public String getF_principal_name() {
        return F_principal_name;
    }

    public void setF_principal_name(String f_principal_name) {
        F_principal_name = f_principal_name;
    }

    public String getF_processing_person_id() {
        return F_processing_person_id;
    }

    public void setF_processing_person_id(String f_processing_person_id) {
        F_processing_person_id = f_processing_person_id;
    }

    public String getF_processing_person_name() {
        return F_processing_person_name;
    }

    public void setF_processing_person_name(String f_processing_person_name) {
        F_processing_person_name = f_processing_person_name;
    }

    public String getF_building_name() {
        return F_building_name;
    }

    public void setF_building_name(String f_building_name) {
        F_building_name = f_building_name;
    }

    public String getREF_ID_() {
        return REF_ID_;
    }

    public void setREF_ID_(String REF_ID_) {
        this.REF_ID_ = REF_ID_;
    }

    public String getF_project_id() {
        return F_project_id;
    }

    public void setF_project_id(String f_project_id) {
        F_project_id = f_project_id;
    }

    public String getF_line_id() {
        return F_line_id;
    }

    public void setF_line_id(String f_line_id) {
        F_line_id = f_line_id;
    }

    public String getF_inspection_work_plan_name() {
        return F_inspection_work_plan_name;
    }

    public void setF_inspection_work_plan_name(String f_inspection_work_plan_name) {
        F_inspection_work_plan_name = f_inspection_work_plan_name;
    }

    public String getF_grid_id() {
        return F_grid_id;
    }

    public void setF_grid_id(String f_grid_id) {
        F_grid_id = f_grid_id;
    }

    public String getF_hang_status() {
        return F_hang_status;
    }

    public void setF_hang_status(String f_hang_status) {
        F_hang_status = f_hang_status;
    }

    public String getF_inspection_work_guidance_name() {
        return F_inspection_work_guidance_name;
    }

    public void setF_inspection_work_guidance_name(String f_inspection_work_guidance_name) {
        F_inspection_work_guidance_name = f_inspection_work_guidance_name;
    }

    public String getF_files() {
        return F_files;
    }

    public void setF_files(String f_files) {
        F_files = f_files;
    }

    public int getF_plan_work_order_state() {
        return F_plan_work_order_state;
    }

    public void setF_plan_work_order_state(int f_plan_work_order_state) {
        F_plan_work_order_state = f_plan_work_order_state;
    }

    public String getF_principal_id() {
        return F_principal_id;
    }

    public void setF_principal_id(String f_principal_id) {
        F_principal_id = f_principal_id;
    }

    @NonNull
    public String getId_() {
        return id_;
    }

    public void setId_(@NonNull String id_) {
        this.id_ = id_;
    }

    public String getF_processing_date() {
        return F_processing_date;
    }

    public void setF_processing_date(String f_processing_date) {
        F_processing_date = f_processing_date;
    }

    public String getF_tit_id() {
        return F_tit_id;
    }

    public void setF_tit_id(String f_tit_id) {
        F_tit_id = f_tit_id;
    }

    public String getF_creation_date() {
        return F_creation_date;
    }

    public void setF_creation_date(String f_creation_date) {
        F_creation_date = f_creation_date;
    }

    public String getF_type_id() {
        return F_type_id;
    }

    public void setF_type_id(String f_type_id) {
        F_type_id = f_type_id;
    }

    public String getF_line_code() {
        return F_line_code;
    }

    public void setF_line_code(String f_line_code) {
        F_line_code = f_line_code;
    }

    public String getF_massif_id() {
        return F_massif_id;
    }

    public void setF_massif_id(String f_massif_id) {
        F_massif_id = f_massif_id;
    }

    public String getF_inspection_work_plan_id() {
        return F_inspection_work_plan_id;
    }

    public void setF_inspection_work_plan_id(String f_inspection_work_plan_id) {
        F_inspection_work_plan_id = f_inspection_work_plan_id;
    }

    public String getF_grid_name() {
        return F_grid_name;
    }

    public void setF_grid_name(String f_grid_name) {
        F_grid_name = f_grid_name;
    }

    public String getF_house_code() {
        return F_house_code;
    }

    public void setF_house_code(String f_house_code) {
        F_house_code = f_house_code;
    }

    public int getF_floor() {
        return F_floor;
    }

    public void setF_floor(int f_floor) {
        F_floor = f_floor;
    }

    public String getF_inspection_work_guidance_id() {
        return F_inspection_work_guidance_id;
    }

    public void setF_inspection_work_guidance_id(String f_inspection_work_guidance_id) {
        F_inspection_work_guidance_id = f_inspection_work_guidance_id;
    }

    public String getF_description() {
        return F_description;
    }

    public void setF_description(String f_description) {
        F_description = f_description;
    }

    public int getF_ot_hours() {
        return F_ot_hours;
    }

    public void setF_ot_hours(int f_ot_hours) {
        F_ot_hours = f_ot_hours;
    }

    public String getF_completion_deadline() {
        return F_completion_deadline;
    }

    public void setF_completion_deadline(String f_completion_deadline) {
        F_completion_deadline = f_completion_deadline;
    }

    public String getF_actual_completion_time() {
        return F_actual_completion_time;
    }

    public void setF_actual_completion_time(String f_actual_completion_time) {
        F_actual_completion_time = f_actual_completion_time;
    }

    public String getF_plan_work_order_code() {
        return F_plan_work_order_code;
    }

    public void setF_plan_work_order_code(String f_plan_work_order_code) {
        F_plan_work_order_code = f_plan_work_order_code;
    }

    public int getF_is_time_out() {
        return F_is_time_out;
    }

    public void setF_is_time_out(int f_is_time_out) {
        F_is_time_out = f_is_time_out;
    }

    public InitData getInitData() {
        return initData;
    }

    public void setInitData(InitData initData) {
        this.initData = initData;
    }

    public List<SubInspectionWorkOrderFlowNode> getSub_inspection_work_order_flow_node() {
        return sub_inspection_work_order_flow_node;
    }

    public void setSub_inspection_work_order_flow_node(List<SubInspectionWorkOrderFlowNode> sub_inspection_work_order_flow_node) {
        this.sub_inspection_work_order_flow_node = sub_inspection_work_order_flow_node;
    }

    public String getF_SEND_REMARK() {
        return F_SEND_REMARK;
    }

    public void setF_SEND_REMARK(String f_SEND_REMARK) {
        F_SEND_REMARK = f_SEND_REMARK;
    }

    public String getF_patrol_line_id() {
        return F_patrol_line_id;
    }

    public void setF_patrol_line_id(String f_patrol_line_id) {
        F_patrol_line_id = f_patrol_line_id;
    }
}
