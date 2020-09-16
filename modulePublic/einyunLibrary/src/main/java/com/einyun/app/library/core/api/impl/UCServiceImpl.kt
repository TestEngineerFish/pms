package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.UCService
import com.einyun.app.library.core.api.proxy.UCSericeImplProxy
import com.einyun.app.library.uc.user.model.*
import com.einyun.app.library.uc.user.net.request.ChangePassRequest
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api.impl
 * @ClassName:      UCServiceImpl
 * @Description:     UC服务提供者
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 15:35
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 15:35
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class UCServiceImpl :UCService {
    override fun updateApp(callBack: CallBack<UpdateAppModel>): LiveData<UpdateAppModel> {
        return proxy.updateApp(callBack)
    }

    override fun getCheckNum(
        phone: String,
        callBack: CallBack<Any>
    ): LiveData<Any> {
        return proxy.getCheckNum(phone,callBack)
    }

    override fun getPhone(account: String, callBack: CallBack<String>): LiveData<String> {
        return proxy.getPhone(account,callBack)    }

    override fun checkkNum(phone: String, code: String, callBack: CallBack<Any>): LiveData<Any> {
        return proxy.checkkNum(phone,code,callBack)
    }

    override fun changePass(
        changePassRequest: ChangePassRequest,
        callBack: CallBack<Any>
    ): LiveData<Any> {
        return proxy.changePass(changePassRequest,callBack)
    }

    var proxy:UCSericeImplProxy= UCSericeImplProxy()
    override fun login(username: String, password: String, callBack: CallBack<UserModel>): LiveData<UserModel> {
        return proxy.login(username,password,callBack)
    }

    override fun userAccount(account: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel> {
        return proxy.userAccount(account,callBack)
    }

    override fun getUser(account: String, userNumber: String?, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel> {
        return proxy.getUser(account,userNumber,callBack)
    }

    override fun userById(userId: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel> {
        return proxy.userById(userId,callBack)
    }

    override fun userByEmail(email: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel> {
        return proxy.userByEmail(email,callBack)
    }

    override fun accountExist(account: String, callBack: CallBack<Boolean>): LiveData<Boolean> {
        return proxy.accountExist(account,callBack)
    }

    override fun isAdmin(callBack: CallBack<Boolean>): LiveData<Boolean> {
        return proxy.isAdmin(callBack)
    }

    override fun userNumberExist(account: String, userNumber: String?, callBack: CallBack<Boolean>): LiveData<Boolean> {
        return proxy.userNumberExist(account,userNumber,callBack)
    }

    override fun updateUser(request: UpdateUserRequest, callBack: CallBack<Boolean>): LiveData<Boolean> {
        return proxy.updateUser(request,callBack)
    }

    override fun getTenantId(code: String, callBack: CallBack<TenantModel>): LiveData<TenantModel> {
        return proxy.getTenantId(code,callBack)
    }

    fun getName(): String {
       return this.javaClass.simpleName
    }
}