package com.einyun.app.library.core.api

import com.einyun.app.base.event.CallBack
import com.einyun.app.library.uc.usercenter.model.OrgModel

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api
 * @ClassName:      UserCenterService
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/15 16:16
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/15 16:16
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface UserCenterService :EinyunService{
    fun listOrChildByOrgId(orgId: String, userId: String, callBack: CallBack<List<OrgModel>>)
}