package com.einyun.app.library.workorder.model

import com.einyun.app.library.resource.workorder.model.Buttons
import com.einyun.app.library.resource.workorder.model.Data
import com.einyun.app.library.resource.workorder.model.ExtensionApplication

class RepairsDetailModel {


    /**
     * formResult : null
     * form : null
     * data : {"customer_repair_model":{"bx_appoint_time_period":"21:00~22:00","fclose_applyer_id":null,"handle_receipt_no":null,"fclose_approve_result":null,"grid_code":null,"housekeeper_name":null,"response_result":null,"return_user":null,"handle_man_hour":null,"return_time":null,"service_unit_fee":null,"bx_cate_lv3_id":"electric_water-heater","fault_desc":null,"line_name":"工程","service_quality_content":null,"state":"for_response","fault_area":null,"fclose_approve_time":null,"app_state":null,"service_unit_rate":null,"u_project":"长城盛世家园分公司","handle_pay_type_id":null,"service_attitude_content":null,"house_code":null,"bx_recorder":"李淑杰","tenant_id":"55614223698362369","fclose_apply_attach":null,"bx_dk_id":"63879794843975685","line_key":"engineering_classification","bx_build_id":null,"bx_user":"zhangsan","bx_time":"2019-12-18 18:46:08","work_ascription":null,"ref_id_":"0","staff_rate":null,"bx_cate_lv1_id":"indoor_repair","assign_grab_state":"3","grab_time":"2019-12-18 18:46:20","handle_pay_time":null,"u_city_area":null,"u_region":null,"return_user_id":null,"building_name":null,"bx_unit_id":null,"response_timeout":null,"assign_grab_user":"李淑杰","pd_user_id":null,"bx_area_id":"indoor","bx_code":"ccssjyeq-GC-BX-20191218180007","fclose_apply_reason":null,"return_visit_timeout":null,"bx_property_ass":"轻微","return_unsatisfy_do":null,"return_score":"0","return_threshold":null,"return_visit_result":null,"staff_fee":null,"c_is_solve":0,"handle_time":null,"dispatch_close":null,"bx_way":"来电","bx_area":"户内","handle_user":null,"ot_pd_user_id":null,"pd_remark":null,"bx_dk":"长城盛世家园二期","proc_inst_id_":"66496797874323461","housekeeper_account":null,"handle_fee":null,"grid_id":null,"return_result":null,"is_grab_overtime":null,"artificial_cost":null,"bx_cate_lv2":"电器","response_user_id":null,"bx_cate_lv1":"户内报修类","bx_property_ass_id":"slight","service_quality_score":"0","bx_cate_lv3":"电热水器","material_cost":null,"project_fee":null,"work_order_timeout":null,"id_":"66496797874324485","receive_user":null,"joint_processor":null,"u_project_id":"63872468703510533","receive_time":null,"close_time":null,"grid_name":null,"unit_name":null,"return_way_id":null,"bx_house_id":null,"bx_appoint_time":"2019-12-18","project_rate":null,"handle_is_paid":null,"bx_way_id":"call_in","bx_recorder_seat_number":null,"fclose_applyer":null,"handle_user_id":null,"u_region_id":null,"bx_recorder_id":"63879813097586693","handle_pay_type":null,"c_deadline_time":"2019-12-25 18:46:08","bx_mobile":"52359038680","fclose_approve_id":null,"c_return_visit_status":null,"return_way":null,"receive_user_id":null,"return_visit_num":null,"bx_user_id":null,"bx_house":null,"pd_user":null,"ot_pd_user":null,"handle_result":null,"ot_pd_time":null,"bx_content":"ceshi","u_city_area_id":null,"handle_timeout":null,"bx_attachment":null,"bx_appoint_time_period_id":"21_22","close_remark":null,"bx_cate_lv2_id":"electrical_equipment","pd_time":null,"response_time":null,"response_user":null,"assign_grab_user_id":"63879813097586693","fclose_apply_time":null,"fclose_is_applying":0,"work_ascription_code":null,"handle_attach":null,"sub_repair_materials":[],"initData":{"repair_materials":{"MAKTX":"","quantity":"","LABST":"","total_price":"","LGORT":"","WERKS":"","LGOBE":"","MEINS":"","NAME1":"","NETPR":"","price":"","name":"","MATKL":"","MATNR":""}}}}
     * opinionList : null
     * permission : null
     */

    var formResult: Any? = null
    var form: Any? = null
    var data: DataBean? = null
    var opinionList: Any? = null
    var permission: Any? = null
    val buttons: List<Buttons>? = null
    var extensionApplication: ArrayList<ExtensionApplication>? = null

    /**
     * 获取强制逼单或申请延期信息
     */
    fun getExtApplication(applyType:Int): ExtensionApplication? {
        if(extensionApplication==null||extensionApplication?.size==0){
            return null
        }
        for(ext in extensionApplication!!){
            if(applyType==ext.applyType){
                return ext
            }
        }
        return null
    }

    var handleList:List<HandleListModel>?=null
    class DataBean {
        /**
         * customer_repair_model : {"bx_appoint_time_period":"21:00~22:00","fclose_applyer_id":null,"handle_receipt_no":null,"fclose_approve_result":null,"grid_code":null,"housekeeper_name":null,"response_result":null,"return_user":null,"handle_man_hour":null,"return_time":null,"service_unit_fee":null,"bx_cate_lv3_id":"electric_water-heater","fault_desc":null,"line_name":"工程","service_quality_content":null,"state":"for_response","fault_area":null,"fclose_approve_time":null,"app_state":null,"service_unit_rate":null,"u_project":"长城盛世家园分公司","handle_pay_type_id":null,"service_attitude_content":null,"house_code":null,"bx_recorder":"李淑杰","tenant_id":"55614223698362369","fclose_apply_attach":null,"bx_dk_id":"63879794843975685","line_key":"engineering_classification","bx_build_id":null,"bx_user":"zhangsan","bx_time":"2019-12-18 18:46:08","work_ascription":null,"ref_id_":"0","staff_rate":null,"bx_cate_lv1_id":"indoor_repair","assign_grab_state":"3","grab_time":"2019-12-18 18:46:20","handle_pay_time":null,"u_city_area":null,"u_region":null,"return_user_id":null,"building_name":null,"bx_unit_id":null,"response_timeout":null,"assign_grab_user":"李淑杰","pd_user_id":null,"bx_area_id":"indoor","bx_code":"ccssjyeq-GC-BX-20191218180007","fclose_apply_reason":null,"return_visit_timeout":null,"bx_property_ass":"轻微","return_unsatisfy_do":null,"return_score":"0","return_threshold":null,"return_visit_result":null,"staff_fee":null,"c_is_solve":0,"handle_time":null,"dispatch_close":null,"bx_way":"来电","bx_area":"户内","handle_user":null,"ot_pd_user_id":null,"pd_remark":null,"bx_dk":"长城盛世家园二期","proc_inst_id_":"66496797874323461","housekeeper_account":null,"handle_fee":null,"grid_id":null,"return_result":null,"is_grab_overtime":null,"artificial_cost":null,"bx_cate_lv2":"电器","response_user_id":null,"bx_cate_lv1":"户内报修类","bx_property_ass_id":"slight","service_quality_score":"0","bx_cate_lv3":"电热水器","material_cost":null,"project_fee":null,"work_order_timeout":null,"id_":"66496797874324485","receive_user":null,"joint_processor":null,"u_project_id":"63872468703510533","receive_time":null,"close_time":null,"grid_name":null,"unit_name":null,"return_way_id":null,"bx_house_id":null,"bx_appoint_time":"2019-12-18","project_rate":null,"handle_is_paid":null,"bx_way_id":"call_in","bx_recorder_seat_number":null,"fclose_applyer":null,"handle_user_id":null,"u_region_id":null,"bx_recorder_id":"63879813097586693","handle_pay_type":null,"c_deadline_time":"2019-12-25 18:46:08","bx_mobile":"52359038680","fclose_approve_id":null,"c_return_visit_status":null,"return_way":null,"receive_user_id":null,"return_visit_num":null,"bx_user_id":null,"bx_house":null,"pd_user":null,"ot_pd_user":null,"handle_result":null,"ot_pd_time":null,"bx_content":"ceshi","u_city_area_id":null,"handle_timeout":null,"bx_attachment":null,"bx_appoint_time_period_id":"21_22","close_remark":null,"bx_cate_lv2_id":"electrical_equipment","pd_time":null,"response_time":null,"response_user":null,"assign_grab_user_id":"63879813097586693","fclose_apply_time":null,"fclose_is_applying":0,"work_ascription_code":null,"handle_attach":null,"sub_repair_materials":[],"initData":{"repair_materials":{"MAKTX":"","quantity":"","LABST":"","total_price":"","LGORT":"","WERKS":"","LGOBE":"","MEINS":"","NAME1":"","NETPR":"","price":"","name":"","MATKL":"","MATNR":""}}}
         */

        var customer_repair_model: CustomerRepairModelBean? = null

        var customer_complain_model: CustomerComplainModelBean? = null

        class CustomerRepairModelBean {
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
             * bx_cate_lv3_id : electric_water-heater
             * fault_desc : null
             * line_name : 工程
             * service_quality_content : null
             * state : for_response
             * fault_area : null
             * fclose_approve_time : null
             * app_state : null
             * service_unit_rate : null
             * u_project : 长城盛世家园分公司
             * handle_pay_type_id : null
             * service_attitude_content : null
             * house_code : null
             * bx_recorder : 李淑杰
             * tenant_id : 55614223698362369
             * fclose_apply_attach : null
             * bx_dk_id : 63879794843975685
             * line_key : engineering_classification
             * bx_build_id : null
             * bx_user : zhangsan
             * bx_time : 2019-12-18 18:46:08
             * work_ascription : null
             * ref_id_ : 0
             * staff_rate : null
             * bx_cate_lv1_id : indoor_repair
             * assign_grab_state : 3
             * grab_time : 2019-12-18 18:46:20
             * handle_pay_time : null
             * u_city_area : null
             * u_region : null
             * return_user_id : null
             * building_name : null
             * bx_unit_id : null
             * response_timeout : null
             * assign_grab_user : 李淑杰
             * pd_user_id : null
             * bx_area_id : indoor
             * bx_code : ccssjyeq-GC-BX-20191218180007
             * fclose_apply_reason : null
             * return_visit_timeout : null
             * bx_property_ass : 轻微
             * return_unsatisfy_do : null
             * return_score : 0
             * return_threshold : null
             * return_visit_result : null
             * staff_fee : null
             * c_is_solve : 0
             * handle_time : null
             * dispatch_close : null
             * bx_way : 来电
             * bx_area : 户内
             * handle_user : null
             * ot_pd_user_id : null
             * pd_remark : null
             * bx_dk : 长城盛世家园二期
             * proc_inst_id_ : 66496797874323461
             * housekeeper_account : null
             * handle_fee : null
             * grid_id : null
             * return_result : null
             * is_grab_overtime : null
             * artificial_cost : null
             * bx_cate_lv2 : 电器
             * response_user_id : null
             * bx_cate_lv1 : 户内报修类
             * bx_property_ass_id : slight
             * service_quality_score : 0
             * bx_cate_lv3 : 电热水器
             * material_cost : null
             * project_fee : null
             * work_order_timeout : null
             * id_ : 66496797874324485
             * receive_user : null
             * joint_processor : null
             * u_project_id : 63872468703510533
             * receive_time : null
             * close_time : null
             * grid_name : null
             * unit_name : null
             * return_way_id : null
             * bx_house_id : null
             * bx_appoint_time : 2019-12-18
             * project_rate : null
             * handle_is_paid : null
             * bx_way_id : call_in
             * bx_recorder_seat_number : null
             * fclose_applyer : null
             * handle_user_id : null
             * u_region_id : null
             * bx_recorder_id : 63879813097586693
             * handle_pay_type : null
             * c_deadline_time : 2019-12-25 18:46:08
             * bx_mobile : 52359038680
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
             * bx_content : ceshi
             * u_city_area_id : null
             * handle_timeout : null
             * bx_attachment : null
             * bx_appoint_time_period_id : 21_22
             * close_remark : null
             * bx_cate_lv2_id : electrical_equipment
             * pd_time : null
             * response_time : null
             * response_user : null
             * assign_grab_user_id : 63879813097586693
             * fclose_apply_time : null
             * fclose_is_applying : 0
             * work_ascription_code : null
             * handle_attach : null
             * sub_repair_materials : []
             * initData : {"repair_materials":{"MAKTX":"","quantity":"","LABST":"","total_price":"","LGORT":"","WERKS":"","LGOBE":"","MEINS":"","NAME1":"","NETPR":"","price":"","name":"","MATKL":"","MATNR":""}}
             */

            var bx_appoint_time_period: String? = null
            var fclose_applyer_id: Any? = null
            var handle_receipt_no: Any? = null
            var fclose_approve_result: Any? = null
            var grid_code: Any? = null
            var housekeeper_name: Any? = null
            var response_result: Any? = null
            var return_user: Any? = null
            var handle_man_hour: Any? = null
            var return_time: Any? = null
            var service_unit_fee: Any? = null
            var bx_cate_lv3_id: String? = null
            var fault_desc: Any? = null
            var line_name: String? = null
            var service_quality_content: Any? = null
            var state: String? = null
            var fault_area: Any? = null
            var fclose_approve_time: Any? = null
            var app_state: Any? = null
            var service_unit_rate: Any? = null
            var u_project: String? = null
            var handle_pay_type_id: Any? = null
            var service_attitude_content: Any? = null
            var house_code: Any? = null
            var bx_recorder: String? = null
            var tenant_id: String? = null
            var fclose_apply_attach: Any? = null
            var bx_dk_id: String? = null
            var line_key: String? = null
            var bx_build_id: Any? = null
            var bx_user: String? = null
            var bx_time: String? = null
            var work_ascription: Any? = null
            var ref_id_: String? = null
            var staff_rate: Any? = null
            var bx_cate_lv1_id: String? = null
            var assign_grab_state: String? = null
            var grab_time: String? = null
            var handle_pay_time: Any? = null
            var u_city_area: Any? = null
            var u_region: Any? = null
            var return_user_id: Any? = null
            var building_name: Any? = null
            var bx_unit_id: Any? = null
            var response_timeout: Any? = null
            var assign_grab_user: String? = null
            var pd_user_id: Any? = null
            var bx_area_id: String? = null
            var bx_code: String? = null
            var fclose_apply_reason: Any? = null
            var return_visit_timeout: Any? = null
            var bx_property_ass: String? = null
            var return_unsatisfy_do: Any? = null
            var return_score: String? = null
            var return_threshold: Any? = null
            var return_visit_result: Any? = null
            var staff_fee: Any? = null
            var c_is_solve: Int = 0
            var handle_time: Any? = null
            var dispatch_close: Any? = null
            var bx_way: String? = null
            var bx_area: String? = null
            var handle_user: Any? = null
            var ot_pd_user_id: Any? = null
            var pd_remark: Any? = null
            var bx_dk: String? = null
            var proc_inst_id_: String? = null
            var housekeeper_account: Any? = null
            var handle_fee: Any? = null
            var grid_id: Any? = null
            var return_result: Any? = null
            var is_grab_overtime: Any? = null
            var artificial_cost: Any? = null
            var bx_cate_lv2: String? = null
            var response_user_id: Any? = null
            var bx_cate_lv1: String? = null
            var bx_property_ass_id: String? = null
            var service_quality_score: String? = null
            var bx_cate_lv3: String? = null
            var material_cost: Any? = null
            var project_fee: Any? = null
            var work_order_timeout: Any? = null
            var id_: String? = null
            var receive_user: Any? = null
            var joint_processor: Any? = null
            var u_project_id: String? = null
            var receive_time: Any? = null
            var close_time: Any? = null
            var grid_name: Any? = null
            var unit_name: Any? = null
            var return_way_id: Any? = null
            var bx_house_id: Any? = null
            var bx_appoint_time: String? = null
            var project_rate: Any? = null
            var handle_is_paid: Any? = null
            var bx_way_id: String? = null
            var bx_recorder_seat_number: Any? = null
            var fclose_applyer: Any? = null
            var handle_user_id: Any? = null
            var u_region_id: Any? = null
            var bx_recorder_id: String? = null
            var handle_pay_type: Any? = null
            var c_deadline_time: String? = null
            var bx_mobile: String? = null
            var fclose_approve_id: Any? = null
            var c_return_visit_status: Any? = null
            var return_way: Any? = null
            var receive_user_id: Any? = null
            var return_visit_num: Any? = null
            var bx_user_id: Any? = null
            var bx_house: Any? = null
            var pd_user: Any? = null
            var ot_pd_user: Any? = null
            var handle_result: Any? = null
            var ot_pd_time: Any? = null
            var bx_content: String? = null
            var u_city_area_id: Any? = null
            var handle_timeout: Any? = null
            var bx_attachment: Any? = null
            var bx_appoint_time_period_id: String? = null
            var close_remark: Any? = null
            var bx_cate_lv2_id: String? = null
            var pd_time: Any? = null
            var response_time: Any? = null
            var response_user: Any? = null
            var assign_grab_user_id: String? = null
            var fclose_apply_time: Any? = null
            var fclose_is_applying: Int = 0
            var work_ascription_code: Any? = null
            var handle_attach: Any? = null
            var initData: InitDataBean? = null
            var sub_repair_materials: List<*>? = null

            class InitDataBean {
                /**
                 * repair_materials : {"MAKTX":"","quantity":"","LABST":"","total_price":"","LGORT":"","WERKS":"","LGOBE":"","MEINS":"","NAME1":"","NETPR":"","price":"","name":"","MATKL":"","MATNR":""}
                 */

                var repair_materials: RepairMaterialsBean? = null

                class RepairMaterialsBean {
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

                    var maktx: String? = null
                    var quantity: String? = null
                    var labst: String? = null
                    var total_price: String? = null
                    var lgort: String? = null
                    var werks: String? = null
                    var lgobe: String? = null
                    var meins: String? = null
                    var namE1: String? = null
                    var netpr: String? = null
                    var price: String? = null
                    var name: String? = null
                    var matkl: String? = null
                    var matnr: String? = null
                }
            }
        }

    }
}
