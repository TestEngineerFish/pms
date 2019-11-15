package com.einyun.app.library.uc.usercenter.repository

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.api.UserCenterService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.uc.usercenter.model.OrgModel
import com.einyun.app.library.uc.usercenter.net.UserCenterServiceApi
import com.einyun.app.library.uc.usercenter.net.request.OrgRequest

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.usercenter.repository
 * @ClassName:      UserCenterRepository
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/14 18:06
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/14 18:06
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class UserCenterRepository() : UserCenterService {
    var serviceApi: UserCenterServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(UserCenterServiceApi::class.java)
    }

    /**
     * 查询组织列表或其子列表
     */
   override fun listOrChildByOrgId(orgId: String, userId: String, callBack: CallBack<List<OrgModel>>) {
        var request = OrgRequest(orgId, userId)
        serviceApi?.listOrChildByOrgId(request)?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, {
                    callBack.onFaild(it)
                })
    }
}