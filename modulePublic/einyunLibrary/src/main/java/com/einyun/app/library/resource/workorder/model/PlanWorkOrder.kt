package com.einyun.app.library.resource.workorder.model

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.model
 * @ClassName:      PlanWorkOrder
 * @Description:     计划工单
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 10:40
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 10:40
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class PlanWorkOrder {
     var proInsId: String? = null
     var orderType: Int = 0
     var createTime: Long = 0
     var F_CREATE_TIME: Long = 0
     var ID_: String? = null
     var F_ORDER_NO: String? = null
     var taskName: String? = null
     var F_WP_NAME: String? = null
     var ownerId: String? = null
     var assigneeId: String? = null
     var taskId: String? = null
     var taskNodeId: String? = null
     var subject: String? = null
     var F_STATUS: String? = null
     var F_EXT_STATUS: Int = 0
     var F_OT_STATUS: Int = 0
}