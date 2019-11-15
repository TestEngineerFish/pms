package com.einyun.app.library.core.api.impl

import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.UserCenterService
import com.einyun.app.library.core.api.proxy.UserCenterServiceImplProxy
import com.einyun.app.library.uc.usercenter.model.OrgModel

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api.impl
 * @ClassName:      UserCenterServiceImpl
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/15 16:17
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/15 16:17
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class UserCenterServiceImpl :UserCenterService{
    var proxy=UserCenterServiceImplProxy()
    override fun listOrChildByOrgId(orgId: String, userId: String, callBack: CallBack<List<OrgModel>>) {
        proxy.listOrChildByOrgId(orgId,userId,callBack)
    }

}