package com.einyun.app.library.workorder.net.request

import com.einyun.app.library.workorder.model.EnquiryBiz

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.net.request
 * @ClassName:      EnquiryStartRequest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 17:57
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 17:57
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class EnquiryStartRequest {
    var boolean:Boolean?=false
    var bizData: EnquiryBiz?=null
    var startFlowParamObject:FlowObject?=null
}