package com.einyun.app.library.uc.usercenter.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.BasicApplication
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.api.UserCenterService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.usercenter.model.HouseModel
import com.einyun.app.library.uc.usercenter.model.OrgModel
import com.einyun.app.library.uc.usercenter.model.WorkStatusModel
import com.einyun.app.library.uc.usercenter.net.URLs
import com.einyun.app.library.uc.usercenter.net.UserCenterServiceApi
import com.einyun.app.library.uc.usercenter.net.request.OrgRequest
import com.einyun.app.library.uc.usercenter.net.request.SearchUserRequest
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse

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
    override fun getHouseByCondition(
        divide: String,
        id: String?,
        callBack: CallBack<List<HouseModel>>
    ): LiveData<List<HouseModel>> {
        val liveData = MutableLiveData<List<HouseModel>>()
        if(id == null){
            serviceApi?.getHouseByCondition(divide)?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState){
                        callBack.call(response.data)
                        liveData.postValue(response.data)
                    }
                }, { error -> callBack.onFaild(error) })
        }else{
            serviceApi?.getHouseByCondition(divide,id)?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState){
                        callBack.call(response.data)
                        liveData.postValue(response.data)
                    }
                }, { error -> callBack.onFaild(error) })
        }
        return liveData
    }

    override fun searchUserByCondition(
        request: SearchUserRequest,
        callBack: CallBack<List<GetMappingByUserIdsResponse>>
    ): LiveData<List<GetMappingByUserIdsResponse>> {
        val liveData = MutableLiveData<List<GetMappingByUserIdsResponse>>()
        serviceApi?.searchUserByCondition(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState){
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun getDisposePerson(
        orgId: String,
        dimCode: String,
        callBack: CallBack<List<OrgModel>>
    ): LiveData<List<OrgModel>> {
        val liveData = MutableLiveData<List<OrgModel>>()
        serviceApi?.getDisposePerson(orgId, dimCode)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState()) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }
    override fun getCheckedPerson(
        orgId: String,
        callBack: CallBack<List<OrgModel>>
    ): LiveData<List<OrgModel>> {
        val liveData = MutableLiveData<List<OrgModel>>()
        serviceApi?.getCheckedPerson(orgId)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState()) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }
    override fun getWorkStatus(userId: String, callBack: CallBack<String>): LiveData<String> {
        val liveData = MutableLiveData<String>()
        serviceApi?.getWorkStatus(userId)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState()) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    if ("-1".equals(response.code)) {
//                        callBack.call("1")
//                        liveData.postValue("1")
                    }
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun updateWorkStatus(
        userId: String,
        userName: String,
        status: String,
        parkId: String,
        parkName: String,
        mark: String,
        note: String,
        callBack: CallBack<String>
    ): LiveData<String> {
        val liveData = MutableLiveData<String>()
        serviceApi?.updateWorkStatus(userId, userName, status,parkId,parkName,mark,note)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState()) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun userCenterUserList(
        userId: String,
        callBack: CallBack<List<OrgModel>>
    ): LiveData<List<OrgModel>> {
        val liveData = MutableLiveData<List<OrgModel>>()
        val url = URLs.URL_USER_CENTER_USER_LIST + userId
        serviceApi?.userCenterUserList(url)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState()) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }


    var serviceApi: UserCenterServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(UserCenterServiceApi::class.java)
    }

    /**
     * 查询组织列表或其子列表
     */
    override fun listOrChildByOrgId(
        orgId: String,
        userId: String,
        callBack: CallBack<List<OrgModel>>
    ) {
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