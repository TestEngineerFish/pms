package com.einyun.app.library.resource.workorder.repository

import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.resource.workorder.net.ResourceWorkOrderServiceApi

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.repository
 * @ClassName:      ResourceWorkOrderRepo
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 10:23
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 10:23
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ResourceWorkOrderRepo {
    var serviceApi: ResourceWorkOrderServiceApi?= null
    init {
        serviceApi=EinyunHttpService.getInstance().getServiceApi(ResourceWorkOrderServiceApi::class.java)
    }


}