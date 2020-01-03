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

}
