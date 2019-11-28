package com.einyun.app.library.core.api.proxy

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.UserCenterService
import com.einyun.app.library.uc.usercenter.model.OrgModel
import com.einyun.app.library.uc.usercenter.repository.UserCenterRepository

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api.proxy
 * @ClassName:      UserCenterServiceImplProxy
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/15 16:18
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/15 16:18
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class UserCenterServiceImplProxy : UserCenterService {
    override fun userCenterUserList(userId: String, callBack: CallBack<List<OrgModel>>): LiveData<List<OrgModel>> {
        return instance?.userCenterUserList(userId, callBack)!!
    }

    var instance: UserCenterService? = null

    constructor() {
        instance = UserCenterRepository()
    }

    override fun listOrChildByOrgId(orgId: String, userId: String, callBack: CallBack<List<OrgModel>>) {
        instance?.listOrChildByOrgId(orgId, userId, callBack)
    }
}