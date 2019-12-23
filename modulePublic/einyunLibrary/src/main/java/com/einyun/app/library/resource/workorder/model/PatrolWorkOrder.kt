package com.einyun.app.library.resource.workorder.model


/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.resource.workorder.model
 * @ClassName: PatrolWorkOrder
 * @Description: 巡查工单
 * @Author: chumingjun
 * @CreateDate: 2019/10/18 10:57
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/18 10:57
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class PatrolWorkOrder {
    var orderType: Int = 0
    var userId: String? = null
    var F_plan_work_order_state: Int = 0
    var F_line_name: String? = null
    var ID_: String? = null
    var subject: String? = null
    var F_creation_date: Long = 0
    var F_line_code: String? = null
    var F_type_id: String? = null
    var auditor_: String? = null
    var F_type_name: String? = null
    var proInsId: String? = null
    var parentInstId: String? = null
    var createTime: Long = 0
    var F_principal_name: String? = null
    var F_plan_work_order_code: String? = null
    var F_inspection_work_plan_name: String? = null
    var auditor_name_: String? = null
    var taskNodeId: String? = null
    var taskId: String? = null
    var assigneeId: String? = null
    var ownerId: String? = null
    var F_patrol_line_id:String?=null
    var is_coming_timeout:Int=0
}
