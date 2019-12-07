package com.einyun.app.library.resource.workorder.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PatrolInfo {
     var taskId: String?=null
     var data: PatrolMain? = null
     var extensionApplication: ExtensionApplication? = null
     var buttons: List<PatrolButton>? = null
     var delayExtensionApplication: DelayExtensionApplication? = null
}

class PatrolButton{
     var id: Int = 0
     var name: String? = null
     var alias: String? = null
     var beforeScript: String? = null
     var afterScript: String? = null
     var groovyScript: String? = null
     var urlForm: String? = null
     var supportScript: Boolean = false
}

class PatrolMain{
     var id: Int = 0
     @SerializedName("zyxcgd")
     var info: PatrolContent? = null
}

class PatrolContent{
     var F_massif_name: String? = null
     var F_line_name: String? = null
     var F_building_id: String? = null
     var F_processing_instructions: String? = null
     var F_project_name: String? = null
     var F_type_name: String? = null
     var F_unit_id: String? = null
     var F_processing_time: String? = null
     var proc_inst_id_: String? = null
     var F_unit_name: String? = null
     var F_tit_name: String? = null
     var F_principal_name: String? = null
     var F_processing_person_id: String? = null
     var F_processing_person_name: String? = null
     var F_building_name: String? = null
     var REF_ID_: String? = null
     var F_project_id: String? = null
     var F_line_id: String? = null
     var F_inspection_work_plan_name: String? = null
     var F_grid_id: String? = null
     var F_hang_status: String? = null
     var F_inspection_work_guidance_name: String? = null
     var F_files: String? = null
     var F_plan_work_order_state: Int = 0
     var F_principal_id: String? = null
     var id_: String?=null
     var F_processing_date: String? = null
     var F_tit_id: String? = null
     var F_creation_date: String? = null
     var F_type_id: String? = null
     var F_line_code: String? = null
     var F_massif_id: String? = null
     var F_inspection_work_plan_id: String? = null
     var F_grid_name: String? = null
     var F_house_code: String? = null
     var F_floor: Int = 0
     var F_inspection_work_guidance_id: String? = null
     var F_description: String? = null
     var F_ot_hours: Int = 0
     var F_completion_deadline: String? = null
     var F_actual_completion_time: String? = null
     var F_plan_work_order_code: String? = null
     var F_is_time_out: Int = 0
     var initData: InitData? = null
     var sub_inspection_work_order_flow_node: List<SubInspectionWorkOrderFlowNode>? =
        null

     var is_sort: Int = 0
     var F_patrol_line_name: String? = null
     var F_duration: Int = 0
}

class ExtensionApplication{
     var id: String? = null
     var poId: String? = null
     var applyType: Int = 0
     var applicationDescription: String? = null
     var applicationState: Int = 0
     var creationDate: String? = null
     var type: Int = 0
     var createdBy: String? = null
     var createdName: String? = null
     var applyFiles: String? = null
}

class DelayExtensionApplication{
     var id: String? = null
     var poId: String? = null
     var applyType: Int = 0
     var extensionDays: Int = 0
     var applicationDescription: String? = null
     var applicationState: Int = 0
     var creationDate: String? = null
     var type: Int = 0
     var createdBy: String? = null
     var approveId: String? = null
     var approveName: String? = null
     var createName: String? = null
     var applyFiles: String? = null
     var auditDate: String? = null
}

class InitData{
     var id: String?=null
     var inspection_work_order_flow_node: InspectionWorkOrderFlowNode? = null
}

class InspectionWorkOrderFlowNode{
     var F_WK_CONTENT: String? = null
     var F_WK_ID: String?=null
     var F_WK_NODE: String? = null
     var F_WK_RESULT: String? = null
}

class SubInspectionWorkOrderFlowNode:Serializable{
     var ref_id_: String? = null
     var F_WK_CONTENT: String? = null
     var F_WK_ID: String? = null
     var id_: String?=null
     var F_WK_NODE: String? = null
     var F_WK_RESULT: String? = null
     var proc_inst_id_: String? = null
    //巡更新新字段
     var is_photo: Int = 0
     var tenant_id: String? = null
     var pic_example_url: String? = null
     var patrol_items: String? = null
     var sign_type: String? = null
     var sort: Int = 0
     var patrol_point_id: String? = null
     var pic_url: String? = null
     var sign_result: Int = 0
     var sign_time: String? = null
}
