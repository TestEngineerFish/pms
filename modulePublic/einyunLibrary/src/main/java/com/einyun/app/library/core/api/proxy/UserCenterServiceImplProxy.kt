package com.einyun.app.library.core.api.proxy

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.UserCenterService
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.usercenter.model.HouseModel
import com.einyun.app.library.uc.usercenter.model.OrgModel
import com.einyun.app.library.uc.usercenter.model.WorkStatusModel
import com.einyun.app.library.uc.usercenter.net.request.SearchUserRequest
import com.einyun.app.library.uc.usercenter.repository.UserCenterRepository
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse

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
    override fun getHouseByCondition(
        divide: String,
        id: String?,
        callBack: CallBack<List<HouseModel>>
    ): LiveData<List<HouseModel>> {
        return instance?.getHouseByCondition(divide,id, callBack)!!
    }

    override fun searchUserByCondition(
        request: SearchUserRequest,
        callBack: CallBack<List<GetMappingByUserIdsResponse>>
    ): LiveData<List<GetMappingByUserIdsResponse>> {
        return instance?.searchUserByCondition(request, callBack)!!
    }

    override fun getDisposePerson(
        orgId: String,
        dimCode: String,
        callBack: CallBack<List<OrgModel>>
    ): LiveData<List<OrgModel>> {
        return instance?.getDisposePerson(orgId, dimCode, callBack)!!
    }

    override fun getCheckedPerson(
        orgId: String,
        callBack: CallBack<List<OrgModel>>
    ): LiveData<List<OrgModel>> {
        return instance?.getCheckedPerson(orgId,  callBack)!!
    }
    override fun getWorkStatus(userId: String, callBack: CallBack<String>): LiveData<String> {
        return instance?.getWorkStatus(userId, callBack)!!
    }

    override fun updateWorkStatus(
        userId: String,
        userName: String,
        status: String,
        callBack: CallBack<List<WorkStatusModel>>
    ): LiveData<List<WorkStatusModel>> {
        return instance?.updateWorkStatus(userId, userName, status, callBack)!!
    }

    override fun userCenterUserList(
        userId: String,
        callBack: CallBack<List<OrgModel>>
    ): LiveData<List<OrgModel>> {
        return instance?.userCenterUserList(userId, callBack)!!
    }

    var instance: UserCenterService? = null

    constructor() {
        instance = UserCenterRepository()
    }

    override fun listOrChildByOrgId(
        orgId: String,
        userId: String,
        callBack: CallBack<List<OrgModel>>
    ) {
        instance?.listOrChildByOrgId(orgId, userId, callBack)
    }
}