package com.einyun.app.library.uc.usercenter.net.request

import com.einyun.app.library.resource.workorder.net.request.PageQueryRequest

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.usercenter.net.request
 * @ClassName:      OrgRequest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/14 17:49
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/14 17:49
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SearchUserRequest {
    var params: UserIds? = null

    class UserIds {
        var roleIdList: List<String>? = null
        var orgIdList: List<String>? = null
    }
}