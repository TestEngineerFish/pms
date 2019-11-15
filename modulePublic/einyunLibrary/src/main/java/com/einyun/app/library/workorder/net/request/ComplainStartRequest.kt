package com.einyun.app.library.workorder.net.request

import com.einyun.app.library.workorder.model.ComplainBiz

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.net.request
 * @ClassName:      ComplainStartQuest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 17:02
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 17:02
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ComplainStartRequest {
    var boolean:Boolean?=false
    var bizData:ComplainBiz?=null
    var startFlowParamObject:FlowObject?=null
}

class FlowObject{
    var flowKey:String?=null
    var vars:VarItem?=null
}

class VarItem{
    var stagingCode:String?=null
}