package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.uc.user.model.*
import com.einyun.app.library.uc.user.net.request.ChangePassRequest
import com.einyun.app.library.uc.user.net.request.GetKaoQingHistoryRequest
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
interface UCService : EinyunService {
    fun getTenantId(code: String, callBack: CallBack<TenantModel>): LiveData<TenantModel>

    /**
     * 用户登陆
     *
     * @return
     */
    fun login(
        username: String,
        password: String,
        code: String,
        uuid: String,
        callBack: CallBack<UserModel>
    ): LiveData<UserModel>

    /**
     * 根据账号获取用户信息
     */
    fun userAccount(account: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel>

    /**
     * 根据账号获取用户信息
     */
    fun getUser(
        account: String,
        userNumber: String?,
        callBack: CallBack<UserInfoModel>
    ): LiveData<UserInfoModel>

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
    fun userNumberExist(
        account: String,
        userNumber: String?,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean>

    /**
     * 更新用户信息
     */
    fun updateUser(request: UpdateUserRequest, callBack: CallBack<Boolean>): LiveData<Boolean>

    fun updateApp(callBack: CallBack<UpdateAppModel>): LiveData<UpdateAppModel>

    /**
     * 获取短信验证码
     * */
    fun getCheckNum(phone: String, callBack: CallBack<Any>): LiveData<Any>

    /**
     * 根据账号获取手机号
     * */
    fun getPhone(account: String, callBack: CallBack<String>): LiveData<String>

    /**
     * 校验短信验证码
     * */
    fun checkkNum(phone: String, code: String, callBack: CallBack<Any>): LiveData<Any>

    /**
     * 修改密码
     * */
    fun changePass(changePassRequest: ChangePassRequest, callBack: CallBack<Any>): LiveData<Any>

    /**
     * 获取考勤范围
     */
    fun getKaoQingSize(orgCode: String, callBack: CallBack<UserInfoModel>): LiveData<UserInfoModel>

    /**
     * 获取所在产业园坐标
     */
    fun getOrgLocation(callBack: CallBack<List<KaoQingOrgModel>>): LiveData<List<KaoQingOrgModel>>

    /**
     * 获取考情历史
     */
    fun getKaoQingHistroy(
        getKaoQingHistoryRequest: GetKaoQingHistoryRequest,
        callBack: CallBack<List<KaoQingHistroyModel>>
    ): LiveData<List<KaoQingHistroyModel>>

    /**
     * 是否有外勤打卡权限
     */
    fun ifKaoQingOut(account: String,callBack: CallBack<List<Param>>): LiveData<List<Param>>

    /**
     * 获取图片验证码
     */
    fun getImgVerify(callBack: CallBack<ImgVerifyModel>): LiveData<ImgVerifyModel>

    /**
     * 刷新token
     */
    fun updateToken(password: String,callBack: CallBack<UserModel>): LiveData<UserModel>

}