package com.einyun.app.library.resource.workorder.model

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.model
 * @ClassName:      WaitCount
 * @Description:     待办统计-计划、巡查、派工单
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 10:29
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 10:29
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class WaitCount {
    var planOrderCount: Int = 0
    var dispatchOrderCount: Int = 0
    var inspectionOrderCount: Int = 0
    var inspectionOrderFlowListIsComing: Int = 0
    var planOrderFlowListIsComing: Int = 0
    var dispatchOrderFlowListIsComing: Int = 0
}