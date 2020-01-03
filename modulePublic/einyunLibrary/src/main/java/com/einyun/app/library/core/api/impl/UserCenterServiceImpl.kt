package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.UserCenterService
import com.einyun.app.library.core.api.proxy.UserCenterServiceImplProxy
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.usercenter.model.HouseModel
import com.einyun.app.library.uc.usercenter.model.OrgModel
import com.einyun.app.library.uc.usercenter.net.request.SearchUserRequest
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse

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
class UserCenterServiceImpl : UserCenterService {
    override fun getHouseByCondition(
        divide: String,
        id: String?,
        callBack: CallBack<List<HouseModel>>
    ): LiveData<List<HouseModel>> {
        return proxy.getHouseByCondition(divide,id, callBack)
    }

    override fun searchUserByCondition(
        request: SearchUserRequest,
        callBack: CallBack<List<GetMappingByUserIdsResponse>>
    ): LiveData<List<GetMappingByUserIdsResponse>> {
        return proxy.searchUserByCondition(request, callBack)
    }

    override fun getDisposePerson(
        orgId: String,
        dimCode: String,
        callBack: CallBack<List<OrgModel>>
    ): LiveData<List<OrgModel>> {
        return proxy.getDisposePerson(orgId, dimCode, callBack)
    }

    override fun updateWorkStatus(
        userId: String,
        userName: String,
        status: String,
        callBack: CallBack<String>
    ): LiveData<String> {
        return proxy.updateWorkStatus(userId, userName, status, callBack)
    }

    override fun getWorkStatus(userId: String, callBack: CallBack<String>): LiveData<String> {
        return proxy.getWorkStatus(userId, callBack)
    }

    override fun userCenterUserList(
        userId: String,
        callBack: CallBack<List<OrgModel>>
    ): LiveData<List<OrgModel>> {
        return proxy.userCenterUserList(userId, callBack)
    }

    var proxy = UserCenterServiceImplProxy()

    override fun listOrChildByOrgId(
        orgId: String,
        userId: String,
        callBack: CallBack<List<OrgModel>>
    ) {
        proxy.listOrChildByOrgId(orgId, userId, callBack)
    }

}