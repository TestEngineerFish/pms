package com.einyun.app.library.uc.user.net

import com.einyun.app.base.http.BaseResponse
import com.einyun.app.library.uc.user.net.request.*
import com.einyun.app.library.uc.user.net.response.*
import io.reactivex.Flowable
import retrofit2.http.*

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.user.net
 * @ClassName:      UserServiceApi
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/16 11:14
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/16 11:14
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface UserServiceApi {
    @GET
    fun getTenantId(@Url url: String): Flowable<TentantResponse>

    /**
     * 登陆
     * @param request
     * @return
     */
    @POST(URLs.URL_USER_LOGIN)
    fun login(@Body request: LoginRequest): Flowable<LoginResponse>

    /**
     * 修改密码
     * @param request
     * @return
     */
    @POST(URLs.URL_UC_CHANGE_PWD)
    fun changePassword(@Body request: ChangePwdRequest): Flowable<BaseResponse<Any>>

    /**
     * 根据用户账号获取用户信息
     */
    @POST(URLs.URL_UC_GET_ACCOUNT)
    fun userAccount(@Part("account") account: String): Flowable<UserResponse>

    /**
     * 获取用户信息
     */
    @GET(URLs.URL_UC_GET_USER)
    fun getUser(
        @Query("account") account: String,
        @Query("userNumber") userNumber: String?
    ): Flowable<UserResponse>

    /**
     *根据用户id获取用户信息
     */
    @GET(URLs.URL_UC_GET_USER_BY_ID)
    fun getUserById(@Query("userId") userId: String): Flowable<UserResponse>

    /**
     *根据用户Email获取用户信息
     */
    @GET(URLs.URL_UC_GET_USER_BY_EMAIL)
    fun getUserByEmail(@Query("email") email: String): Flowable<UserResponse>

    /**
     *查询账号是否已存在
     */
    @GET(URLs.URL_UC_GET_USER_EXIST)
    fun isAccountExist(@Query("account") account: String): Flowable<BaseResponse<Any>>

    /**
     *获取当前用户是否超级管理员
     */
    @GET(URLs.URL_UC_IS_ADMIN)
    fun isAdmin(): Flowable<BaseResponse<*>>

    /**
     *查询工号是否已存在
     */
    @GET(URLs.URL_UC_IS_NUMBER_EXIST)
    fun isUserNumberExist(
        @Query("account") account: String,
        @Query("userNumber") userNumber: String?
    ): Flowable<BaseResponse<Any>>

    /**
     *
     *修改用户基本信息
     */
    @PUT(URLs.URL_UC_UPDATE_USER)
    fun updateUserInfo(@Body request: UpdateUserRequest): Flowable<BaseResponse<Any>>

    @POST(URLs.URL_APP_UPDATE)
    fun updateApp(@Body updateAppRequest: UpdateAppRequest): Flowable<UpdateAppResponse>

    /**
     *获取验证码
     */
    @POST(URLs.URL_GET_VERIFY_CODE)
    fun getCheckNum(@Body checkNumRequest: CheckNumRequest): Flowable<CheckNumResponse>

    /**
     * 获取手机号
     * */
    @GET(URLs.URL_GET_PHONE_BY_ACCOUNT)
    fun getPhone(@Query("account") account: String): Flowable<BaseResponse<String>>

    /**
     *校验验证码
     */
    @POST(URLs.URL_CHECK_VERIFY_CODE)
    fun checkNum(@Body checkNumRequest: CheckNumRequest): Flowable<CheckNumResponse>

    /**
     *修改密码
     */
    @POST(URLs.URL_CHANGE_PASS)
    fun changePass(@Body changePassRequest: ChangePassRequest): Flowable<CheckNumResponse>

    /**
     * 获取考勤范围
     */
    @GET(URLs.URL_GET_KAOQING_SIZE)
    fun getKaoQingSize(
        @Query("orgCode") orgCode: String
    ): Flowable<UserResponse>

    /**
     * 获取考勤范围
     */
    @POST(URLs.URL_ORG_LOCATION)
    fun getOrgLocation(): Flowable<KaoQingOrgResponse>

    /**
     * 获取打卡历史
     */
    @POST(URLs.URL_KAOQING_HISTROY)
    fun getKaoQingHistroy(@Body getKaoQingHistoryRequest: GetKaoQingHistoryRequest): Flowable<KaoQingHistroyResponse>


    /**
     * 是否有外勤打卡权限
     */
    @GET(URLs.URL_KAOQING_IF_OUT)
    fun ifOutKaoQing(@Query("account") account: String): Flowable<ParamResponse>
}