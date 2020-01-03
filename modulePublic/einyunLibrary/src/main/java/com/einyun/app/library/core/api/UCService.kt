package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UpdateAppModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api
 * @ClassName:      UCService
 * @Description:    UC服务接口，高级抽象，约束
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 14:46
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 14:46
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface UCService :EinyunService{
    fun getTenantId(code: String, callBack: CallBack<TenantModel>): LiveData<TenantModel>

    /**
     * 用户登陆
     *
     * @return
     */
    fun login(username: String, password: String, callBack: CallBack<UserModel>): LiveData<UserModel>

    /**
     * 根据账号获取用户信息
     */
    fun userAccount(account: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel>

    /**
     * 根据账号获取用户信息
     */
    fun getUser(account: String, userNumber: String?, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel>

    /**
     * 根据用户ID获取用户信息
     */
    fun userById(userId: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel>

    /**
     * 根据用户Email获取用户信息
     */
    fun userByEmail(email: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel>

    /**
     * 判断账户是否存在
     */
    fun accountExist(account: String, callBack: CallBack<Boolean>): LiveData<Boolean>

    /**
     * 判断账户是否存在
     */
    fun isAdmin(callBack: CallBack<Boolean>): LiveData<Boolean>

    /**
     * 判断账户是否存在
     */
    fun userNumberExist(account: String, userNumber: String?, callBack: CallBack<Boolean>): LiveData<Boolean>

    /**
     * 更新用户信息
     */
    fun updateUser(request: UpdateUserRequest, callBack: CallBack<Boolean>): LiveData<Boolean>

    fun updateApp(callBack: CallBack<UpdateAppModel>):LiveData<UpdateAppModel>
}