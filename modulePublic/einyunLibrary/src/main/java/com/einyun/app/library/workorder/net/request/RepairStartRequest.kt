package com.einyun.app.library.workorder.net.request

import com.einyun.app.library.workorder.model.RepairBiz

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.net.request
 * @ClassName:      RepairStartRequest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 17:52
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 17:52
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class RepairStartRequest {
    var boolean:Boolean?=false
    var bizData: RepairBiz?=null
    var startFlowParamObject:FlowObject?=null
}