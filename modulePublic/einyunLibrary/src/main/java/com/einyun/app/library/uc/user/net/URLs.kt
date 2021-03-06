package com.einyun.app.library.uc.user.net

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.user.login.net
 * @ClassName: URLs
 * @Description: 用户模块URL
 *               《客服工单流程的接口v1.2》
 * @Author: chumingjun
 * @CreateDate: 2019/08/30 09:44
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/30 09:44
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
object URLs {

    const val URL_UC_DOMAIN="/uc"
    /**
     * 登陆
     */
    const val URL_USER_LOGIN = URL_UC_DOMAIN+"/auth"

    /**
     * 获取tentantid
     */
    const val URL_USER_GET_TENTANTID = "/datasource/api/saas/v1/tenant/getByCode/"

    /**
     *  用户修改密码
     */
    const val URL_UC_CHANGE_PWD= URL_UC_DOMAIN+"/api/user/v1/user/changUserPsd"

    /**
     * 根据用户账号获取用户信息
     */
    const val URL_UC_GET_ACCOUNT= URL_UC_DOMAIN+"/api/user/v1/user/getByAccount"

    /**
     *  根据用户标识获取用户信息
     */
    const val URL_UC_GET_USER= URL_UC_DOMAIN+"/api/user/v1/user/getUser"

    /**
     * 根据用户id获取用户信息
     */
    const val URL_UC_GET_USER_BY_ID= URL_UC_DOMAIN+"/api/user/v1/user/getUserById"

    /**
     * 根据email查询用户信息
     */
    const val URL_UC_GET_USER_BY_EMAIL= URL_UC_DOMAIN+"/api/user/v1/user/getUserByEmail"

    /**
     * 查询账号是否已存在
     */
    const val URL_UC_GET_USER_EXIST= URL_UC_DOMAIN+"/api/user/v1/user/isAccountExist"


    /**
     * 更新用户
     */
    const val URL_UC_UPDATE_USER= URL_UC_DOMAIN+"/api/user/v1/user/updateUser"

    /**
     * 获取当前用户是否超级管理员
     */
    const val URL_UC_IS_ADMIN= URL_UC_DOMAIN+"/api/user/v1/user/isAdmin"

    /**
     * 查询工号是否已存在
     */
    const val URL_UC_IS_NUMBER_EXIST= URL_UC_DOMAIN+"/api/user/v1/user/isUserNumberExist"

    /**
     * 下载头像
     */
    const val URL_UC_DOWNLOAD_PORTRAIT= URL_UC_DOMAIN+"/api/user/v1/user/portrait/{%s}/{%s}"

    /**
     * 上传头像
     */
    const val URL_UC_UPLOAD_PORTRAIT= URL_UC_DOMAIN+"/api/user/v1/user/uploadPortrait"

    const val URL_APP_UPDATE = "/appcenter/api/appConf/v1/appbbConf/isUp"

    /**
     * 获取验证码
     * */
    const val URL_GET_VERIFY_CODE = "/sms/sms/v1/sendVerificationCode"
    /**
     * 根据账号获取手机号
     * */
    const val URL_GET_PHONE_BY_ACCOUNT = "/user-center/api/usercenter/v1/ucUser/getMobile"

    /**
     * 校验验证码
     * */
    const val URL_CHECK_VERIFY_CODE = "/sms/sms/v1/verifyVerificationCode"

    /**
     * 修改密码
     * */
    const val URL_CHANGE_PASS= "/user-center/api/usercenter/v1/ucUser/updatePwd"

    /**
     * 修改密码
     * */
    const val URL_GET_KAOQING_SIZE= "uc/api/org/v1/orgParam/getOrgParams"

    /**
     * 获取所在产业园所有坐标
     * */
    const val URL_ORG_LOCATION= "user-center/api/usercenter/v1/ucOrg/getUserCyyzb"

    /**
     * 获取打卡记录
     * */
    const val URL_KAOQING_HISTROY= "user-center/api/usercenter/v1/ucWorkHistory/getRecordList"

    /**
     * 是否有外勤打卡权限
     * */
    const val URL_KAOQING_IF_OUT= "uc/api/user/v1/user/getUserParams"

    /**
     * 获取图片验证码
     * */
    const val URL_GET_IMG_VERIFY= "/uc/base/checkCode/captchaImage"
    /**
     * 刷新token
     * */
    const val URL_UPDATE_TOKEN= "/uc/msso/refreshTokens"
}
