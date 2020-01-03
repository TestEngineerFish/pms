package com.einyun.app.library.resource.workorder.net.request

import com.google.gson.annotations.SerializedName

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.net.request
 * @ClassName:      PatrolPageRequest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 10:48
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 10:48
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ResourceTypeRequest : PageRquest() {
    var massifId: String? = null
    var resourceTypeCode: String? = null
}
