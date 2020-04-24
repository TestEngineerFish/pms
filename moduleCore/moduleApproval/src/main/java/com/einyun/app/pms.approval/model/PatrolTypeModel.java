package com.einyun.app.pms.approval.model;

import java.util.List;

public class PatrolTypeModel {

    /**
     * data : {"zyxcgd":{"F_line_name":"环境","F_project_name":"ops-金域蓝湾","F_close":null,"F_type_name":"公区保洁巡查","F_unit_id":null,"proc_inst_id_":"75576089228940294","F_unit_name":null,"is_sort":null,"F_processing_person_id":"66929190485622790","F_project_id":"ops-54649c63-8887-4369-a277-e3465f62f856","F_line_id":"482887","F_principal_id":"66929190485622790","id_":"75576089228941318","F_processing_date":null,"F_tit_id":"66654654028578820","F_creation_date":"2020-03-25 00:35:16","F_type_id":"car_cleaning_patrol_post","F_line_code":"environmental_classification","F_ext_status":null,"F_massif_id":"ops-26B81BCE-29A0-4982-B49A-A553C973B7E7","F_grid_name":null,"F_floor":null,"F_inspection_work_guidance_id":"56fcba7a-7c78-47bd-b640-42ef0a1cd9db","tenant_id":"55614223698362369","F_massif_name":"ops-金域蓝湾","F_building_id":null,"F_processing_instructions":"处理通过","F_patrol_line_id":null,"F_processing_time":"2020-03-25 16:07:36","F_duration":null,"F_real_duration":null,"F_tit_name":"环境服务单元项目负责人","F_principal_name":"徐玲玲","F_processing_person_name":"徐玲玲","F_building_name":null,"F_inspection_work_plan_name":"金域蓝湾流程巡查计划","F_grid_id":null,"F_hang_status":"0","F_inspection_work_guidance_name":"金域蓝湾流程巡查指导","F_files":"[{\"success\":true,\"id\":\"75578170140524550\",\"name\":\"file.jpg\",\"path\":\"55614223698362369/xll/2020/3/75578170140524550.jpg\",\"size\":27056}]","F_plan_work_order_state":4,"F_patrol_line_name":null,"F_inspection_work_plan_id":"df3b713f060c4544b36a1fc076420211","F_description":null,"F_ot_hours":5,"F_completion_deadline":"2020-03-25 05:35:16","F_actual_completion_time":"2020-03-25 16:07:36","F_plan_work_order_code":"community_JYLW-HJ-XC-20200325150025","F_is_time_out":0,"sub_inspection_work_order_flow_node":[{"pic_example_url":null,"tenant_id":"55614223698362369","F_WK_CONTENT":"工作事项02","is_photo":null,"id_":"75578170140525574","patrol_point_id":null,"F_WK_NODE":"工作节点02","sort":null,"proc_inst_id_":"75576089228940294","sign_time":null,"ref_id_":"75576089228941318","F_WK_ID":"2","patrol_items":null,"F_WK_RESULT":"1","sign_result":0,"pic_url":null,"sign_type":null},{"pic_example_url":null,"tenant_id":"55614223698362369","F_WK_CONTENT":"工作事项01","is_photo":null,"id_":"75578170140526598","patrol_point_id":null,"F_WK_NODE":"工作节点01","sort":null,"proc_inst_id_":"75576089228940294","sign_time":null,"ref_id_":"75576089228941318","F_WK_ID":"1","patrol_items":null,"F_WK_RESULT":"1","sign_result":0,"pic_url":null,"sign_type":null}],"initData":{"inspection_work_order_flow_node":{"pic_example_url":"","F_WK_CONTENT":"","is_photo":"","patrol_point_id":"","F_WK_NODE":"","sort":"","sign_time":"","F_WK_ID":"","patrol_items":"","F_WK_RESULT":"","pic_url":"","sign_result":"","sign_type":""}}}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * zyxcgd : {"F_line_name":"环境","F_project_name":"ops-金域蓝湾","F_close":null,"F_type_name":"公区保洁巡查","F_unit_id":null,"proc_inst_id_":"75576089228940294","F_unit_name":null,"is_sort":null,"F_processing_person_id":"66929190485622790","F_project_id":"ops-54649c63-8887-4369-a277-e3465f62f856","F_line_id":"482887","F_principal_id":"66929190485622790","id_":"75576089228941318","F_processing_date":null,"F_tit_id":"66654654028578820","F_creation_date":"2020-03-25 00:35:16","F_type_id":"car_cleaning_patrol_post","F_line_code":"environmental_classification","F_ext_status":null,"F_massif_id":"ops-26B81BCE-29A0-4982-B49A-A553C973B7E7","F_grid_name":null,"F_floor":null,"F_inspection_work_guidance_id":"56fcba7a-7c78-47bd-b640-42ef0a1cd9db","tenant_id":"55614223698362369","F_massif_name":"ops-金域蓝湾","F_building_id":null,"F_processing_instructions":"处理通过","F_patrol_line_id":null,"F_processing_time":"2020-03-25 16:07:36","F_duration":null,"F_real_duration":null,"F_tit_name":"环境服务单元项目负责人","F_principal_name":"徐玲玲","F_processing_person_name":"徐玲玲","F_building_name":null,"F_inspection_work_plan_name":"金域蓝湾流程巡查计划","F_grid_id":null,"F_hang_status":"0","F_inspection_work_guidance_name":"金域蓝湾流程巡查指导","F_files":"[{\"success\":true,\"id\":\"75578170140524550\",\"name\":\"file.jpg\",\"path\":\"55614223698362369/xll/2020/3/75578170140524550.jpg\",\"size\":27056}]","F_plan_work_order_state":4,"F_patrol_line_name":null,"F_inspection_work_plan_id":"df3b713f060c4544b36a1fc076420211","F_description":null,"F_ot_hours":5,"F_completion_deadline":"2020-03-25 05:35:16","F_actual_completion_time":"2020-03-25 16:07:36","F_plan_work_order_code":"community_JYLW-HJ-XC-20200325150025","F_is_time_out":0,"sub_inspection_work_order_flow_node":[{"pic_example_url":null,"tenant_id":"55614223698362369","F_WK_CONTENT":"工作事项02","is_photo":null,"id_":"75578170140525574","patrol_point_id":null,"F_WK_NODE":"工作节点02","sort":null,"proc_inst_id_":"75576089228940294","sign_time":null,"ref_id_":"75576089228941318","F_WK_ID":"2","patrol_items":null,"F_WK_RESULT":"1","sign_result":0,"pic_url":null,"sign_type":null},{"pic_example_url":null,"tenant_id":"55614223698362369","F_WK_CONTENT":"工作事项01","is_photo":null,"id_":"75578170140526598","patrol_point_id":null,"F_WK_NODE":"工作节点01","sort":null,"proc_inst_id_":"75576089228940294","sign_time":null,"ref_id_":"75576089228941318","F_WK_ID":"1","patrol_items":null,"F_WK_RESULT":"1","sign_result":0,"pic_url":null,"sign_type":null}],"initData":{"inspection_work_order_flow_node":{"pic_example_url":"","F_WK_CONTENT":"","is_photo":"","patrol_point_id":"","F_WK_NODE":"","sort":"","sign_time":"","F_WK_ID":"","patrol_items":"","F_WK_RESULT":"","pic_url":"","sign_result":"","sign_type":""}}}
         */

        private ZyxcgdBean zyxcgd;

        public ZyxcgdBean getZyxcgd() {
            return zyxcgd;
        }

        public void setZyxcgd(ZyxcgdBean zyxcgd) {
            this.zyxcgd = zyxcgd;
        }

        public static class ZyxcgdBean {
            /**
             * F_line_name : 环境
             * F_project_name : ops-金域蓝湾
             * F_close : null
             * F_type_name : 公区保洁巡查
             * F_unit_id : null
             * proc_inst_id_ : 75576089228940294
             * F_unit_name : null
             * is_sort : null
             * F_processing_person_id : 66929190485622790
             * F_project_id : ops-54649c63-8887-4369-a277-e3465f62f856
             * F_line_id : 482887
             * F_principal_id : 66929190485622790
             * id_ : 75576089228941318
             * F_processing_date : null
             * F_tit_id : 66654654028578820
             * F_creation_date : 2020-03-25 00:35:16
             * F_type_id : car_cleaning_patrol_post
             * F_line_code : environmental_classification
             * F_ext_status : null
             * F_massif_id : ops-26B81BCE-29A0-4982-B49A-A553C973B7E7
             * F_grid_name : null
             * F_floor : null
             * F_inspection_work_guidance_id : 56fcba7a-7c78-47bd-b640-42ef0a1cd9db
             * tenant_id : 55614223698362369
             * F_massif_name : ops-金域蓝湾
             * F_building_id : null
             * F_processing_instructions : 处理通过
             * F_patrol_line_id : null
             * F_processing_time : 2020-03-25 16:07:36
             * F_duration : null
             * F_real_duration : null
             * F_tit_name : 环境服务单元项目负责人
             * F_principal_name : 徐玲玲
             * F_processing_person_name : 徐玲玲
             * F_building_name : null
             * F_inspection_work_plan_name : 金域蓝湾流程巡查计划
             * F_grid_id : null
             * F_hang_status : 0
             * F_inspection_work_guidance_name : 金域蓝湾流程巡查指导
             * F_files : [{"success":true,"id":"75578170140524550","name":"file.jpg","path":"55614223698362369/xll/2020/3/75578170140524550.jpg","size":27056}]
             * F_plan_work_order_state : 4
             * F_patrol_line_name : null
             * F_inspection_work_plan_id : df3b713f060c4544b36a1fc076420211
             * F_description : null
             * F_ot_hours : 5
             * F_completion_deadline : 2020-03-25 05:35:16
             * F_actual_completion_time : 2020-03-25 16:07:36
             * F_plan_work_order_code : community_JYLW-HJ-XC-20200325150025
             * F_is_time_out : 0
             * sub_inspection_work_order_flow_node : [{"pic_example_url":null,"tenant_id":"55614223698362369","F_WK_CONTENT":"工作事项02","is_photo":null,"id_":"75578170140525574","patrol_point_id":null,"F_WK_NODE":"工作节点02","sort":null,"proc_inst_id_":"75576089228940294","sign_time":null,"ref_id_":"75576089228941318","F_WK_ID":"2","patrol_items":null,"F_WK_RESULT":"1","sign_result":0,"pic_url":null,"sign_type":null},{"pic_example_url":null,"tenant_id":"55614223698362369","F_WK_CONTENT":"工作事项01","is_photo":null,"id_":"75578170140526598","patrol_point_id":null,"F_WK_NODE":"工作节点01","sort":null,"proc_inst_id_":"75576089228940294","sign_time":null,"ref_id_":"75576089228941318","F_WK_ID":"1","patrol_items":null,"F_WK_RESULT":"1","sign_result":0,"pic_url":null,"sign_type":null}]
             * initData : {"inspection_work_order_flow_node":{"pic_example_url":"","F_WK_CONTENT":"","is_photo":"","patrol_point_id":"","F_WK_NODE":"","sort":"","sign_time":"","F_WK_ID":"","patrol_items":"","F_WK_RESULT":"","pic_url":"","sign_result":"","sign_type":""}}
             */

            private String F_line_name;
            private String F_project_name;
            private Object F_close;
            private String F_type_name;
            private Object F_unit_id;
            private String proc_inst_id_;
            private Object F_unit_name;
            private Object is_sort;
            private String F_processing_person_id;
            private String F_project_id;
            private String F_line_id;
            private String F_principal_id;
            private String id_;
            private Object F_processing_date;
            private String F_tit_id;
            private String F_creation_date;
            private String F_type_id;
            private String F_line_code;
            private Object F_ext_status;
            private String F_massif_id;
            private Object F_grid_name;
            private Object F_floor;
            private String F_inspection_work_guidance_id;
            private String tenant_id;
            private String F_massif_name;
            private Object F_building_id;
            private String F_processing_instructions;
            private Object F_patrol_line_id;
            private String F_processing_time;
            private Object F_duration;
            private Object F_real_duration;
            private String F_tit_name;
            private String F_principal_name;
            private String F_processing_person_name;
            private Object F_building_name;
            private String F_inspection_work_plan_name;
            private Object F_grid_id;
            private String F_hang_status;
            private String F_inspection_work_guidance_name;
            private String F_files;
            private int F_plan_work_order_state;
            private Object F_patrol_line_name;
            private String F_inspection_work_plan_id;
            private Object F_description;
            private int F_ot_hours;
            private String F_completion_deadline;
            private String F_actual_completion_time;
            private String F_plan_work_order_code;
            private int F_is_time_out;
            private InitDataBean initData;
            private List<SubInspectionWorkOrderFlowNodeBean> sub_inspection_work_order_flow_node;

            public String getF_line_name() {
                return F_line_name;
            }

            public void setF_line_name(String F_line_name) {
                this.F_line_name = F_line_name;
            }

            public String getF_project_name() {
                return F_project_name;
            }

            public void setF_project_name(String F_project_name) {
                this.F_project_name = F_project_name;
            }

            public Object getF_close() {
                return F_close;
            }

            public void setF_close(Object F_close) {
                this.F_close = F_close;
            }

            public String getF_type_name() {
                return F_type_name;
            }

            public void setF_type_name(String F_type_name) {
                this.F_type_name = F_type_name;
            }

            public Object getF_unit_id() {
                return F_unit_id;
            }

            public void setF_unit_id(Object F_unit_id) {
                this.F_unit_id = F_unit_id;
            }

            public String getProc_inst_id_() {
                return proc_inst_id_;
            }

            public void setProc_inst_id_(String proc_inst_id_) {
                this.proc_inst_id_ = proc_inst_id_;
            }

            public Object getF_unit_name() {
                return F_unit_name;
            }

            public void setF_unit_name(Object F_unit_name) {
                this.F_unit_name = F_unit_name;
            }

            public Object getIs_sort() {
                return is_sort;
            }

            public void setIs_sort(Object is_sort) {
                this.is_sort = is_sort;
            }

            public String getF_processing_person_id() {
                return F_processing_person_id;
            }

            public void setF_processing_person_id(String F_processing_person_id) {
                this.F_processing_person_id = F_processing_person_id;
            }

            public String getF_project_id() {
                return F_project_id;
            }

            public void setF_project_id(String F_project_id) {
                this.F_project_id = F_project_id;
            }

            public String getF_line_id() {
                return F_line_id;
            }

            public void setF_line_id(String F_line_id) {
                this.F_line_id = F_line_id;
            }

            public String getF_principal_id() {
                return F_principal_id;
            }

            public void setF_principal_id(String F_principal_id) {
                this.F_principal_id = F_principal_id;
            }

            public String getId_() {
                return id_;
            }

            public void setId_(String id_) {
                this.id_ = id_;
            }

            public Object getF_processing_date() {
                return F_processing_date;
            }

            public void setF_processing_date(Object F_processing_date) {
                this.F_processing_date = F_processing_date;
            }

            public String getF_tit_id() {
                return F_tit_id;
            }

            public void setF_tit_id(String F_tit_id) {
                this.F_tit_id = F_tit_id;
            }

            public String getF_creation_date() {
                return F_creation_date;
            }

            public void setF_creation_date(String F_creation_date) {
                this.F_creation_date = F_creation_date;
            }

            public String getF_type_id() {
                return F_type_id;
            }

            public void setF_type_id(String F_type_id) {
                this.F_type_id = F_type_id;
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

            public String getF_massif_id() {
                return F_massif_id;
            }

            public void setF_massif_id(String F_massif_id) {
                this.F_massif_id = F_massif_id;
            }

            public Object getF_grid_name() {
                return F_grid_name;
            }

            public void setF_grid_name(Object F_grid_name) {
                this.F_grid_name = F_grid_name;
            }

            public Object getF_floor() {
                return F_floor;
            }

            public void setF_floor(Object F_floor) {
                this.F_floor = F_floor;
            }

            public String getF_inspection_work_guidance_id() {
                return F_inspection_work_guidance_id;
            }

            public void setF_inspection_work_guidance_id(String F_inspection_work_guidance_id) {
                this.F_inspection_work_guidance_id = F_inspection_work_guidance_id;
            }

            public String getTenant_id() {
                return tenant_id;
            }

            public void setTenant_id(String tenant_id) {
                this.tenant_id = tenant_id;
            }

            public String getF_massif_name() {
                return F_massif_name;
            }

            public void setF_massif_name(String F_massif_name) {
                this.F_massif_name = F_massif_name;
            }

            public Object getF_building_id() {
                return F_building_id;
            }

            public void setF_building_id(Object F_building_id) {
                this.F_building_id = F_building_id;
            }

            public String getF_processing_instructions() {
                return F_processing_instructions;
            }

            public void setF_processing_instructions(String F_processing_instructions) {
                this.F_processing_instructions = F_processing_instructions;
            }

            public Object getF_patrol_line_id() {
                return F_patrol_line_id;
            }

            public void setF_patrol_line_id(Object F_patrol_line_id) {
                this.F_patrol_line_id = F_patrol_line_id;
            }

            public String getF_processing_time() {
                return F_processing_time;
            }

            public void setF_processing_time(String F_processing_time) {
                this.F_processing_time = F_processing_time;
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

            public String getF_tit_name() {
                return F_tit_name;
            }

            public void setF_tit_name(String F_tit_name) {
                this.F_tit_name = F_tit_name;
            }

            public String getF_principal_name() {
                return F_principal_name;
            }

            public void setF_principal_name(String F_principal_name) {
                this.F_principal_name = F_principal_name;
            }

            public String getF_processing_person_name() {
                return F_processing_person_name;
            }

            public void setF_processing_person_name(String F_processing_person_name) {
                this.F_processing_person_name = F_processing_person_name;
            }

            public Object getF_building_name() {
                return F_building_name;
            }

            public void setF_building_name(Object F_building_name) {
                this.F_building_name = F_building_name;
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

            public String getF_hang_status() {
                return F_hang_status;
            }

            public void setF_hang_status(String F_hang_status) {
                this.F_hang_status = F_hang_status;
            }

            public String getF_inspection_work_guidance_name() {
                return F_inspection_work_guidance_name;
            }

            public void setF_inspection_work_guidance_name(String F_inspection_work_guidance_name) {
                this.F_inspection_work_guidance_name = F_inspection_work_guidance_name;
            }

            public String getF_files() {
                return F_files;
            }

            public void setF_files(String F_files) {
                this.F_files = F_files;
            }

            public int getF_plan_work_order_state() {
                return F_plan_work_order_state;
            }

            public void setF_plan_work_order_state(int F_plan_work_order_state) {
                this.F_plan_work_order_state = F_plan_work_order_state;
            }

            public Object getF_patrol_line_name() {
                return F_patrol_line_name;
            }

            public void setF_patrol_line_name(Object F_patrol_line_name) {
                this.F_patrol_line_name = F_patrol_line_name;
            }

            public String getF_inspection_work_plan_id() {
                return F_inspection_work_plan_id;
            }

            public void setF_inspection_work_plan_id(String F_inspection_work_plan_id) {
                this.F_inspection_work_plan_id = F_inspection_work_plan_id;
            }

            public Object getF_description() {
                return F_description;
            }

            public void setF_description(Object F_description) {
                this.F_description = F_description;
            }

            public int getF_ot_hours() {
                return F_ot_hours;
            }

            public void setF_ot_hours(int F_ot_hours) {
                this.F_ot_hours = F_ot_hours;
            }

            public String getF_completion_deadline() {
                return F_completion_deadline;
            }

            public void setF_completion_deadline(String F_completion_deadline) {
                this.F_completion_deadline = F_completion_deadline;
            }

            public String getF_actual_completion_time() {
                return F_actual_completion_time;
            }

            public void setF_actual_completion_time(String F_actual_completion_time) {
                this.F_actual_completion_time = F_actual_completion_time;
            }

            public String getF_plan_work_order_code() {
                return F_plan_work_order_code;
            }

            public void setF_plan_work_order_code(String F_plan_work_order_code) {
                this.F_plan_work_order_code = F_plan_work_order_code;
            }

            public int getF_is_time_out() {
                return F_is_time_out;
            }

            public void setF_is_time_out(int F_is_time_out) {
                this.F_is_time_out = F_is_time_out;
            }

            public InitDataBean getInitData() {
                return initData;
            }

            public void setInitData(InitDataBean initData) {
                this.initData = initData;
            }

            public List<SubInspectionWorkOrderFlowNodeBean> getSub_inspection_work_order_flow_node() {
                return sub_inspection_work_order_flow_node;
            }

            public void setSub_inspection_work_order_flow_node(List<SubInspectionWorkOrderFlowNodeBean> sub_inspection_work_order_flow_node) {
                this.sub_inspection_work_order_flow_node = sub_inspection_work_order_flow_node;
            }

            public static class InitDataBean {
                /**
                 * inspection_work_order_flow_node : {"pic_example_url":"","F_WK_CONTENT":"","is_photo":"","patrol_point_id":"","F_WK_NODE":"","sort":"","sign_time":"","F_WK_ID":"","patrol_items":"","F_WK_RESULT":"","pic_url":"","sign_result":"","sign_type":""}
                 */

                private InspectionWorkOrderFlowNodeBean inspection_work_order_flow_node;

                public InspectionWorkOrderFlowNodeBean getInspection_work_order_flow_node() {
                    return inspection_work_order_flow_node;
                }

                public void setInspection_work_order_flow_node(InspectionWorkOrderFlowNodeBean inspection_work_order_flow_node) {
                    this.inspection_work_order_flow_node = inspection_work_order_flow_node;
                }

                public static class InspectionWorkOrderFlowNodeBean {
                    /**
                     * pic_example_url :
                     * F_WK_CONTENT :
                     * is_photo :
                     * patrol_point_id :
                     * F_WK_NODE :
                     * sort :
                     * sign_time :
                     * F_WK_ID :
                     * patrol_items :
                     * F_WK_RESULT :
                     * pic_url :
                     * sign_result :
                     * sign_type :
                     */

                    private String pic_example_url;
                    private String F_WK_CONTENT;
                    private String is_photo;
                    private String patrol_point_id;
                    private String F_WK_NODE;
                    private String sort;
                    private String sign_time;
                    private String F_WK_ID;
                    private String patrol_items;
                    private String F_WK_RESULT;
                    private String pic_url;
                    private String sign_result;
                    private String sign_type;

                    public String getPic_example_url() {
                        return pic_example_url;
                    }

                    public void setPic_example_url(String pic_example_url) {
                        this.pic_example_url = pic_example_url;
                    }

                    public String getF_WK_CONTENT() {
                        return F_WK_CONTENT;
                    }

                    public void setF_WK_CONTENT(String F_WK_CONTENT) {
                        this.F_WK_CONTENT = F_WK_CONTENT;
                    }

                    public String getIs_photo() {
                        return is_photo;
                    }

                    public void setIs_photo(String is_photo) {
                        this.is_photo = is_photo;
                    }

                    public String getPatrol_point_id() {
                        return patrol_point_id;
                    }

                    public void setPatrol_point_id(String patrol_point_id) {
                        this.patrol_point_id = patrol_point_id;
                    }

                    public String getF_WK_NODE() {
                        return F_WK_NODE;
                    }

                    public void setF_WK_NODE(String F_WK_NODE) {
                        this.F_WK_NODE = F_WK_NODE;
                    }

                    public String getSort() {
                        return sort;
                    }

                    public void setSort(String sort) {
                        this.sort = sort;
                    }

                    public String getSign_time() {
                        return sign_time;
                    }

                    public void setSign_time(String sign_time) {
                        this.sign_time = sign_time;
                    }

                    public String getF_WK_ID() {
                        return F_WK_ID;
                    }

                    public void setF_WK_ID(String F_WK_ID) {
                        this.F_WK_ID = F_WK_ID;
                    }

                    public String getPatrol_items() {
                        return patrol_items;
                    }

                    public void setPatrol_items(String patrol_items) {
                        this.patrol_items = patrol_items;
                    }

                    public String getF_WK_RESULT() {
                        return F_WK_RESULT;
                    }

                    public void setF_WK_RESULT(String F_WK_RESULT) {
                        this.F_WK_RESULT = F_WK_RESULT;
                    }

                    public String getPic_url() {
                        return pic_url;
                    }

                    public void setPic_url(String pic_url) {
                        this.pic_url = pic_url;
                    }

                    public String getSign_result() {
                        return sign_result;
                    }

                    public void setSign_result(String sign_result) {
                        this.sign_result = sign_result;
                    }

                    public String getSign_type() {
                        return sign_type;
                    }

                    public void setSign_type(String sign_type) {
                        this.sign_type = sign_type;
                    }
                }
            }

            public static class SubInspectionWorkOrderFlowNodeBean {
                /**
                 * pic_example_url : null
                 * tenant_id : 55614223698362369
                 * F_WK_CONTENT : 工作事项02
                 * is_photo : null
                 * id_ : 75578170140525574
                 * patrol_point_id : null
                 * F_WK_NODE : 工作节点02
                 * sort : null
                 * proc_inst_id_ : 75576089228940294
                 * sign_time : null
                 * ref_id_ : 75576089228941318
                 * F_WK_ID : 2
                 * patrol_items : null
                 * F_WK_RESULT : 1
                 * sign_result : 0
                 * pic_url : null
                 * sign_type : null
                 */

                private Object pic_example_url;
                private String tenant_id;
                private String F_WK_CONTENT;
                private Object is_photo;
                private String id_;
                private Object patrol_point_id;
                private String F_WK_NODE;
                private Object sort;
                private String proc_inst_id_;
                private Object sign_time;
                private String ref_id_;
                private String F_WK_ID;
                private Object patrol_items;
                private String F_WK_RESULT;
                private int sign_result;
                private Object pic_url;
                private Object sign_type;

                public Object getPic_example_url() {
                    return pic_example_url;
                }

                public void setPic_example_url(Object pic_example_url) {
                    this.pic_example_url = pic_example_url;
                }

                public String getTenant_id() {
                    return tenant_id;
                }

                public void setTenant_id(String tenant_id) {
                    this.tenant_id = tenant_id;
                }

                public String getF_WK_CONTENT() {
                    return F_WK_CONTENT;
                }

                public void setF_WK_CONTENT(String F_WK_CONTENT) {
                    this.F_WK_CONTENT = F_WK_CONTENT;
                }

                public Object getIs_photo() {
                    return is_photo;
                }

                public void setIs_photo(Object is_photo) {
                    this.is_photo = is_photo;
                }

                public String getId_() {
                    return id_;
                }

                public void setId_(String id_) {
                    this.id_ = id_;
                }

                public Object getPatrol_point_id() {
                    return patrol_point_id;
                }

                public void setPatrol_point_id(Object patrol_point_id) {
                    this.patrol_point_id = patrol_point_id;
                }

                public String getF_WK_NODE() {
                    return F_WK_NODE;
                }

                public void setF_WK_NODE(String F_WK_NODE) {
                    this.F_WK_NODE = F_WK_NODE;
                }

                public Object getSort() {
                    return sort;
                }

                public void setSort(Object sort) {
                    this.sort = sort;
                }

                public String getProc_inst_id_() {
                    return proc_inst_id_;
                }

                public void setProc_inst_id_(String proc_inst_id_) {
                    this.proc_inst_id_ = proc_inst_id_;
                }

                public Object getSign_time() {
                    return sign_time;
                }

                public void setSign_time(Object sign_time) {
                    this.sign_time = sign_time;
                }

                public String getRef_id_() {
                    return ref_id_;
                }

                public void setRef_id_(String ref_id_) {
                    this.ref_id_ = ref_id_;
                }

                public String getF_WK_ID() {
                    return F_WK_ID;
                }

                public void setF_WK_ID(String F_WK_ID) {
                    this.F_WK_ID = F_WK_ID;
                }

                public Object getPatrol_items() {
                    return patrol_items;
                }

                public void setPatrol_items(Object patrol_items) {
                    this.patrol_items = patrol_items;
                }

                public String getF_WK_RESULT() {
                    return F_WK_RESULT;
                }

                public void setF_WK_RESULT(String F_WK_RESULT) {
                    this.F_WK_RESULT = F_WK_RESULT;
                }

                public int getSign_result() {
                    return sign_result;
                }

                public void setSign_result(int sign_result) {
                    this.sign_result = sign_result;
                }

                public Object getPic_url() {
                    return pic_url;
                }

                public void setPic_url(Object pic_url) {
                    this.pic_url = pic_url;
                }

                public Object getSign_type() {
                    return sign_type;
                }

                public void setSign_type(Object sign_type) {
                    this.sign_type = sign_type;
                }
            }
        }
    }
}
