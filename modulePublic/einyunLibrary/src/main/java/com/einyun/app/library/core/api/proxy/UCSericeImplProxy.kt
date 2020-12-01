package com.einyun.app.library.core.api.proxy

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.UCService
import com.einyun.app.library.uc.user.model.*
import com.einyun.app.library.uc.user.net.request.ChangePassRequest
import com.einyun.app.library.uc.user.net.request.GetKaoQingHistoryRequest
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest
import com.einyun.app.library.uc.user.repository.UserRepository

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.core.api.proxy
 * @ClassName: UCSericeImplProxy
 * @Description: UC服务代理类
 *                单一原则，开闭原则，李氏置换原则
 * @Author: chumingjun
 * @CreateDate: 2019/09/26 16:04
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/26 16:04
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class UCSericeImplProxy : UCService {
    override fun updateApp(callBack: CallBack<UpdateAppModel>): LiveData<UpdateAppModel> {
        return instance?.updateApp(callBack)!!
    }

    override fun getCheckNum(
        phone: String,
        callBack: CallBack<Any>
    ): LiveData<Any> {
        return instance?.getCheckNum(phone, callBack)!!
    }

    override fun getPhone(account: String, callBack: CallBack<String>): LiveData<String> {
        return instance?.getPhone(account, callBack)!!
    }

    override fun checkkNum(phone: String, code: String, callBack: CallBack<Any>): LiveData<Any> {
        return instance?.checkkNum(phone, code, callBack)!!
    }

    override fun changePass(
        changePassRequest: ChangePassRequest,
        callBack: CallBack<Any>
    ): LiveData<Any> {
        return instance?.changePass(changePassRequest, callBack)!!
    }

    override fun getKaoQingSize(
        orgCode: String,
        callBack: CallBack<UserInfoModel>
    ): LiveData<UserInfoModel> {
        return instance?.getKaoQingSize(orgCode, callBack)!!
    }

    override fun getOrgLocation(callBack: CallBack<List<KaoQingOrgModel>>): LiveData<List<KaoQingOrgModel>> {
        return instance?.getOrgLocation(callBack)!!
    }

    override fun getKaoQingHistroy(
        getKaoQingHistoryRequest: GetKaoQingHistoryRequest,
        callBack: CallBack<List<KaoQingHistroyModel>>
    ): LiveData<List<KaoQingHistroyModel>> {
        return instance?.getKaoQingHistroy(getKaoQingHistoryRequest, callBack)!!
    }

    override fun ifKaoQingOut(
        account: String,
        callBack: CallBack<List<Param>>
    ): LiveData<List<Param>> {
        return instance?.ifKaoQingOut(account, callBack)!!
    }

    private var instance: UCService? = null

    constructor() {
        //数据代理，灵活更换实现
        instance = UserRepository()//真实实现，可替换
    }

    fun getName(): String {
        return this.javaClass.simpleName
    }

    override fun getTenantId(code: String, callBack: CallBack<TenantModel>): LiveData<TenantModel> {
        return instance?.getTenantId(code, callBack)!!
    }

    override fun login(
        username: String,
        password: String,
        callBack: CallBack<UserModel>
    ): LiveData<UserModel> {
        return instance?.login(username, password, callBack)!!
    }

    override fun userAccount(
        account: String,
        callBack: CallBack<UserInfoModel>
    ): LiveData<UserInfoModel> {
        return instance?.userAccount(account, callBack)!!
    }

    override fun getUser(
        account: String,
        userNumber: String?,
        callBack: CallBack<UserInfoModel>
    ): LiveData<UserInfoModel> {
        return instance?.getUser(account, userNumber, callBack)!!
    }

    override fun userById(
        userId: String,
        callBack: CallBack<UserInfoModel>
    ): LiveData<UserInfoModel> {
        return instance?.userById(userId, callBack)!!
    }

    override fun userByEmail(
        email: String,
        callBack: CallBack<UserInfoModel>
    ): LiveData<UserInfoModel> {
        return instance?.userByEmail(email, callBack)!!
    }

    override fun accountExist(account: String, callBack: CallBack<Boolean>): LiveData<Boolean> {
        return instance?.accountExist(account, callBack)!!
    }

    override fun isAdmin(callBack: CallBack<Boolean>): LiveData<Boolean> {
        return instance?.isAdmin(callBack)!!
    }

    override fun userNumberExist(
        account: String,
        userNumber: String?,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.userNumberExist(account, userNumber, callBack)!!
    }

    override fun updateUser(
        request: UpdateUserRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.updateUser(request, callBack)!!
    }

}
