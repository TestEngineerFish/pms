package com.einyun.app.library.uc.user.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.api.UCService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.URLs
import com.einyun.app.library.uc.user.net.UserServiceApi
import com.einyun.app.library.uc.user.net.request.ChangePwdRequest
import com.einyun.app.library.uc.user.net.request.LoginRequest
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest
import com.einyun.app.library.uc.user.net.response.LoginResponse
import com.einyun.app.library.uc.user.net.response.TentantResponse

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.user.repository
 * @ClassName:      UserRepository
 * @Description:     用户数据处理
 *                    目前直接从网络交互，没有本地缓存
 * @Author:         chumingjun
 * @CreateDate:     2019/09/16 11:31
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/16 11:31
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class UserRepository :UCService{
    var serviceApi: UserServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(UserServiceApi::class.java)
    }

    override fun getTenantId(code: String, callBack: CallBack<TenantModel>): LiveData<TenantModel> {
        val liveData = MutableLiveData<TenantModel>()
        val url = URLs.URL_USER_GET_TENTANTID + code
        serviceApi?.getTenantId(url)?.compose(RxSchedulers.inIoMain<TentantResponse>())
                ?.subscribe({ tentantResponse ->
                    if (tentantResponse.isState) {
                        EinyunHttpService.getInstance().tenantId(tentantResponse.data.id)
                        liveData.postValue(tentantResponse.getData())
                        callBack.call(tentantResponse.getData())
                    } else {
                        tentantResponse.msg = "企业编码填写错误"
                        callBack.onFaild(EinyunHttpException(tentantResponse))
                    }
                }, { error -> callBack.onFaild(error) })
        return liveData
    }

    /**
     * 用户登陆
     *
     * @return
     */
    override fun login(username: String, password: String, callBack: CallBack<UserModel>): LiveData<UserModel> {
        val user = MutableLiveData<UserModel>()
        val request = LoginRequest()
        request.setUsername(username)
        request.setPassword(password)
        serviceApi?.login(request)
                ?.compose(RxSchedulers.inIoMain<LoginResponse>())
                ?.subscribe({ loginResponse: LoginResponse ->
                    if (loginResponse.isState) {
                        EinyunHttpService.getInstance().authorToken(loginResponse.data.token)
                        user.postValue(loginResponse.getData())//livedata数据填充，UI层自动感知变化
                        callBack.call(loginResponse.getData()) //回调处理其他逻辑
                    } else {
                        callBack.onFaild(EinyunHttpException(loginResponse))
                    }
                }, { error -> callBack.onFaild(error) })
        return user
    }

    /**
     * 修改密码
     */
    fun changePassword(account: String, oldPwd: String, newPwd: String, userNumber: String, callBack: CallBack<BaseResponse<*>>): LiveData<BaseResponse<*>> {
        var liveData = MutableLiveData<BaseResponse<*>>()
        var request = ChangePwdRequest(account, newPwd, oldPwd, userNumber)
        serviceApi?.changePassword(request)?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response)
                        callBack?.call(response)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     * 根据账号获取用户信息
     */
    override fun userAccount(account: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel> {
        var liveData = MutableLiveData<UserInfoModel>()
        serviceApi?.userAccount(account)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe(
                        { response ->
                            if (response.isState) {
                                liveData.postValue(response?.data)
                                callBack.call(response.data)
                            } else {
                                callBack.onFaild(EinyunHttpException(response))
                            }
                        },
                        {
                            callBack.onFaild(it)
                        }
                )
        return liveData
    }

    /**
     * 根据账号获取用户信息
     */
    override fun getUser(account: String, userNumber: String?, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel> {
        var liveData = MutableLiveData<UserInfoModel>()
        serviceApi?.getUser(account, userNumber)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }

    /**
     * 根据用户ID获取用户信息
     */
    override fun userById(userId: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel> {
        var liveData = MutableLiveData<UserInfoModel>()
        serviceApi?.getUserById(userId)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }

    /**
     * 根据用户Email获取用户信息
     */
    override fun userByEmail(email: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel> {
        var liveData = MutableLiveData<UserInfoModel>()
        serviceApi?.getUserByEmail(email)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }

    /**
     * 判断账户是否存在
     */
    override fun accountExist(account: String, callBack: CallBack<Boolean>): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.isAccountExist(account)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    liveData.postValue(response.isState)
                    callBack.call(response.isState)
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }

    /**
     * 判断账户是否存在
     */
    override fun isAdmin(callBack: CallBack<Boolean>): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.isAdmin()
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    liveData.postValue(response.isState)
                    callBack.call(response.isState)
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }

    /**
     * 判断账户是否存在
     */
    override fun userNumberExist(account: String, userNumber: String?, callBack: CallBack<Boolean>): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.isUserNumberExist(account, userNumber)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    liveData.postValue(response.isState)
                    callBack.call(response.isState)
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }

    /**
     * 更新用户信息
     */
    override fun updateUser(request: UpdateUserRequest, callBack: CallBack<Boolean>): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.updateUserInfo(request)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    liveData.postValue(response.isState)
                    callBack.call(response.isState)
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }
}