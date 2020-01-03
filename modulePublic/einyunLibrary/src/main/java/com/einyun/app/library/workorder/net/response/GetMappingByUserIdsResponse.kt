package com.einyun.app.library.workorder.net.response

import com.einyun.app.base.http.BaseResponse

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.net.response
 * @ClassName:      AuditCountResponse
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 11:11
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 11:11
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class GetMappingByUserIdsResponse {
    var id: String? = null
    var pendingCount: Int? = 0
    var account: String? = null
    var email: String? = null
    var fullname: String? = ""
    var mobile: String? = ""
    var status: Int? = -1
}